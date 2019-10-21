package com.epam.esm.controller;

import com.epam.esm.action.AbstractAction;
import com.epam.esm.aspect.aspectsannotation.Requested;
import com.epam.esm.aspect.aspectsannotation.Timed;
import com.epam.esm.dto.DtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Entity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteConstraintException;
import com.epam.esm.exception.FindObjectException;
import com.epam.esm.service.TagService;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class we use to works with web side.
 * We have different methods which doing different actions.
 * It's crud, search and sort methods.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@RestController
@Transactional
@RequestMapping("/tags")
public class TagController extends AbstractAction {

    /**
     * TagService object.
     */
    private TagService tagService;

    /**
     * messageSource
     */
    private ResourceBundleMessageSource messageSource;

    /**
     * Constructor with parameters.
     * @param tagService object.
     * @param messageSource object.
     */
    public TagController(TagService tagService, ResourceBundleMessageSource messageSource) {
        this.tagService = tagService;
        this.messageSource = messageSource;
    }

    /**
     * Method ti get tag by id.
     * @param id element to find.
     * @return list with one object.
     */
    @Timed
    @Requested
    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<Entity> getTagById(@PathVariable("id") Integer id,
                                                      HttpServletRequest request) throws FindObjectException {
        Tag tag = tagService.findById(id);
        checkEntityExistence(tag);
        return new ResponseEntity<>(DtoConverter.convertTag(tag), HttpStatus.OK);
    }
    /**
     * Select all tag objects.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<List<Entity>> getTags(@RequestParam(value = "page", required = false)
                                                                Integer page,
                                                   @RequestParam(value = "limit", required = false)
                                                                Integer limit,
                                                   HttpServletRequest request) throws FindObjectException {
        List<Entity> tags = tagService
                .findAll(page, limit)
                .stream()
                .map(DtoConverter::convertTag)
                .collect(Collectors.toList());
        checkList(tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Method to find the most popular tag.
     * @return list with tag.
     */
    @Timed
    @Requested
    @GetMapping(value = "/popular", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<Entity> getMostPopularTag(HttpServletRequest request)
            throws FindObjectException {
        Tag tag = tagService.findMostPopularTag();
        checkEntityExistence(tag);
        return new ResponseEntity<>(DtoConverter.convertTag(tag), HttpStatus.OK);
    }

    /**
     * Method to create new tag.
     * @param tagDto to create.
     */
    @Timed
    @Requested
    @PostMapping(produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> createTag(@Valid @RequestBody TagDto tagDto,
                                                          HttpServletRequest request) {
        if (!checkEntity(tagService.findByName(tagDto.getName()))) {
            return new ResponseEntity<>(messageSource.getMessage("create", new Object[]{},
                    Locale.forLanguageTag(checkLang(request))), HttpStatus.BAD_REQUEST);
        }
        tagService.create((Tag) DtoConverter.convertTagDto(tagDto));
        return new ResponseEntity<>(messageSource.getMessage("tagCreated", new Object[]{},
                Locale.forLanguageTag(checkLang(request)))
                + tagService.findByName(tagDto.getName()).getId(), HttpStatus.CREATED);
    }

    /**
     * Method to delete tag.
     */
    @Timed
    @Requested
    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> deleteTag(@PathVariable("id") Integer id,
                                                          HttpServletRequest request)
            throws DeleteConstraintException, FindObjectException {
        checkEntityExistence(tagService.findById(id));
        int deleted = tagService.delete(id);
        return new ResponseEntity<>(messageSource.getMessage("tagDeleted", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + deleted, HttpStatus.OK);
    }
}
