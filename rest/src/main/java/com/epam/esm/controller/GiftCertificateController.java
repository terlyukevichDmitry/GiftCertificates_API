package com.epam.esm.controller;

import com.epam.esm.action.AbstractAction;
import com.epam.esm.aspect.aspectsannotation.Requested;
import com.epam.esm.aspect.aspectsannotation.Timed;
import com.epam.esm.dto.DtoConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.*;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftCertificateService;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class we use to works with web side.
 * We have different methods which doing different actions.
 * It's crud, search and sort methods.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Transactional
@RestController
@RequestMapping("/giftCertificates")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:9002" })
public class GiftCertificateController extends AbstractAction {

    /**
     * GiftCertificateService object.
     */
    private GiftCertificateService giftCertificateService;

    /**
     * resourceBundle object.
     */
    private ResourceBundleMessageSource resourceBundle;

    /**
     * Constructor to initialize elements.
     * @param giftCertificateService object.
     * @param resourceBundle object.
     */
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     ResourceBundleMessageSource resourceBundle) {
        this.giftCertificateService = giftCertificateService;
        this.resourceBundle = resourceBundle;
    }

    /**
     * Method ro get giftCertificate by id.
     * @param id element.
     * @return list with one element.
     */
    @Timed
    @Requested
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public @ResponseBody ResponseEntity<Entity> getGiftCertificateById(@PathVariable("id") Integer id,
                                                                  HttpServletRequest request)
            throws FindObjectException {
        GiftCertificate giftCertificate = giftCertificateService.findByCertificateId(id);
        checkEntityExistence(giftCertificate);
        return new ResponseEntity<>(DtoConverter.convertGiftCertificate(giftCertificate), HttpStatus.OK);
    }

    /**
     * Method to search giftCertificates by Tag name.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(produces = {"application/json"})
    public @ResponseBody ResponseEntity<List<Entity>> getByParameters(@RequestParam(value = "tagname", required = false)
                                                                    String tagName,
                                                           @RequestParam(value = "name", required = false)
                                                                    String partOfName,
                                                           @RequestParam(value = "description", required = false)
                                                                        String partDescription,
                                                           @RequestParam(value = "price", required = false)
                                                                        Double price,
                                                           @RequestParam(value = "duration", required = false)
                                                                        Integer duration,
                                                           @RequestParam(value = "orderby", required = false)
                                                                        String orderBy,
                                                           @RequestParam(value = "page", required = false)
                                                                        Integer page,
                                                           @RequestParam(value = "limit", required = false)
                                                                        Integer limit,
                                                           HttpServletRequest request) throws FindObjectException {
        Map<String, Object> queryElements = new LinkedHashMap<>();
        checkStringParameter(tagName, partOfName, partDescription, price, duration, orderBy, page, limit,
                queryElements);
        List<Entity> giftCertificates = giftCertificateService
                .findSortQuery(queryElements)
                .stream()
                .map(DtoConverter::convertGiftCertificate)
                .collect(Collectors.toList());
        checkList(giftCertificates);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Method to check elements.
     * @param tagName object.
     * @param partOfName object.
     * @param partDescription object.
     * @param orderBy object.
     * @param argMap object.
     */
    private void checkStringParameter(
             String tagName,
             String partOfName,
             String partDescription,
             Double price,
             Integer duration,
             String orderBy,
             Integer page,
             Integer limit,
             Map<String, Object> argMap) {

        if (tagName != null) {
            argMap.put("tagName", tagName);
        }

        if (partOfName != null) {
            argMap.put("partOfName", '%' + partOfName + '%');
        }

        if (partDescription != null) {
            argMap.put("partDescription", '%' + partDescription + '%');
        }

        if (price != null) {
            argMap.put("price", price);
        }

        if (duration != null) {
            argMap.put("duration", duration);
        }

        if (orderBy != null) {
            argMap.put("orderBy", orderBy);
        }

        if (page != null && limit != null) {
            argMap.put("page", page);
            argMap.put("limit", limit);
        }
    }

    /**
     * Method to create new giftCertificate.
     */
    @Timed
    @Requested
    @PostMapping(produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> createGift(@Valid @RequestBody GiftCertificateDto giftCertificate,
                                                           HttpServletRequest request) {
        giftCertificateService.create((GiftCertificate) DtoConverter.convertGiftCertificateDto(giftCertificate));
        List<GiftCertificate> giftCertificates = giftCertificateService.findByName(giftCertificate.getName());
        return new ResponseEntity<>(resourceBundle.getMessage("created", new Object[]{},
                Locale.forLanguageTag(checkLang(request)))
                + giftCertificateService.findByName(giftCertificate.getName())
                .get(giftCertificates.size() - 1).getId(), HttpStatus.CREATED);
    }

    /**
     * Method to delete giftCertificate.
     */
    @Timed
    @Requested
    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> deleteGift(@PathVariable("id") Integer id,
                                                           HttpServletRequest request)
            throws DeleteConstraintException, FindObjectException {
        checkEntityExistence(giftCertificateService.findByCertificateId(id));
        int deleted = giftCertificateService.delete(id);
        return new ResponseEntity<>(resourceBundle.getMessage("deleted", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + deleted, HttpStatus.OK);
    }

    /**
     * Method to update giftCertificate.
     */
    @Timed
    @Requested
    @PutMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> updateGift(@PathVariable("id") Integer id,
                                                           @RequestBody GiftCertificateDto giftCertificateDto,
                                                           HttpServletRequest request) throws FindObjectException {
        checkEntityExistence(giftCertificateService.findByCertificateId(id));
        giftCertificateDto.setId(id);
        giftCertificateService.update((GiftCertificate) DtoConverter.convertGiftCertificateDto(giftCertificateDto));
        return new ResponseEntity<>(resourceBundle.getMessage("updated", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + id, HttpStatus.OK);
    }

    /**
     * Method to update giftCertificate.
     */
    @Timed
    @Requested
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> updatePriceForGift(@PathVariable("id") Integer id,
                                                                   @RequestBody Double price,
                                                                   HttpServletRequest request)
            throws FindObjectException {
        checkEntityExistence(giftCertificateService.findByCertificateId(id));
        if (price < 0) {
            return new ResponseEntity<>(resourceBundle.getMessage("negativePrice", new Object[]{},
                    Locale.forLanguageTag(checkLang(request))), HttpStatus.BAD_REQUEST);
        }
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        updatedGiftCertificate.setId(id);
        updatedGiftCertificate.setPrice(price);
        giftCertificateService.updatePrice(updatedGiftCertificate);
        return new ResponseEntity<>(
                resourceBundle.getMessage("updatedPrice", new Object[]{},
                        Locale.forLanguageTag(checkLang(request))) + id
                        + resourceBundle.getMessage("isUp", new Object[]{},
                        Locale.forLanguageTag(checkLang(request))), HttpStatus.OK);
    }
}
