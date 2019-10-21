package com.epam.esm.controller;

import com.epam.esm.action.AbstractAction;
import com.epam.esm.aspect.aspectsannotation.Requested;
import com.epam.esm.aspect.aspectsannotation.Timed;
import com.epam.esm.dto.DtoConverter;
import com.epam.esm.entity.Entity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DeleteConstraintException;
import com.epam.esm.exception.FindObjectException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class we use to works with web side.
 * We have different methods which doing different actions.
 * It's crud and search methods.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@RestController
@Transactional
@RequestMapping("/users")
public class UserController extends AbstractAction {

    /**
     * userService element.
     */
    private UserService userService;
    /**
     * purchaseService element.
     */
    private PurchaseService purchaseService;
    /**
     * giftCertificateService element.
     */
    private GiftCertificateService giftCertificateService;
    /**
     * passwordEncoder element.
     */
    private PasswordEncoder passwordEncoder;
    /**
     * messageSource element.
     */
    private ResourceBundleMessageSource messageSource;

    /**
     * Constructor with parameters.
     * @param userService object.
     * @param purchaseService object.
     * @param giftCertificateService object.
     * @param passwordEncoder object.
     * @param messageSource object.
     */
    public UserController(UserService userService,
                          PurchaseService purchaseService,
                          GiftCertificateService giftCertificateService,
                          PasswordEncoder passwordEncoder,
                          ResourceBundleMessageSource messageSource) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.giftCertificateService = giftCertificateService;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    /**
     * Method to get all users.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<List<Entity>> getUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit, HttpServletRequest request)
            throws FindObjectException {
        List<User> users = userService.findAll(page, limit);
        checkList(users);
        return new ResponseEntity<>(users.stream().map(DtoConverter::convertUser).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * Method to find user by id.
     * @param userId element to find.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(value = "/{userId}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<Entity> getUsers(@PathVariable("userId") Integer userId,
                                                    HttpServletRequest request) throws FindObjectException {
        User user = userService.findById(userId);
        checkEntityExistence(user);
        user.setPassword(null);
        user.setLogin(null);
        return new ResponseEntity<>(DtoConverter.convertUser(user), HttpStatus.OK);
    }

    /**
     * Method to get all users.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(value = "/purchases", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<List<Entity>> getPurchaseByUserId(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            HttpServletRequest request) throws FindObjectException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByLogin(((UserDetails) principal).getUsername());
        checkEntityExistence(user);
        List<Purchase> purchases = purchaseService.findPurchaseByUserId(user.getId(), page, limit);
        checkList(purchases);
        return new ResponseEntity<>(purchases.stream()
                .map(DtoConverter::convertPurchase)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Method to get all users.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(value = "/purchases/{giftId}",produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<List<Entity>> getPurchaseById(@PathVariable("giftId") Integer giftId,
                                                                      @RequestParam(value = "page", required = false)
                                                                                  Integer page,
                                                                      @RequestParam(value = "limit", required = false)
                                                                                  Integer limit,
                                                                          HttpServletRequest request)
            throws FindObjectException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByLogin(((UserDetails) principal).getUsername());
        checkEntityExistence(user);
        List<Purchase> purchases = purchaseService.findById(user.getId(), giftId);
        checkList(purchases);
        return new ResponseEntity<>(
                purchases
                        .stream()
                        .map(DtoConverter::convertPurchase)
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Method to find all giftCertificates in different users.
     * @param id element.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(value = "/{id}/purchases", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<List<Entity>> getUsersGiftCertificates(@PathVariable("id") Integer id,
                                                                    @RequestParam(value = "page",
                                                                             required = false) Integer page,
                                                                    @RequestParam(value = "limit",
                                                                             required = false) Integer limit)
            throws FindObjectException {
        List<Purchase> purchases = purchaseService.findPurchaseByUserId(id, page, limit);
        checkList(purchases);
        return new ResponseEntity<>(purchases
                        .stream()
                        .map(DtoConverter::convertPurchase)
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Method to find gift cert by id in user by id.
     * @param userId element.
     * @param giftId element.
     * @return list.
     */
    @Timed
    @Requested
    @GetMapping(value = "/{userId}/purchases/{giftId}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<List<Entity>> getUserGiftCertiifcateByGiftId(
            @PathVariable("userId") Integer userId, @PathVariable("giftId") Integer giftId)
            throws FindObjectException {
        List<Purchase> purchases = purchaseService.findById(userId, giftId);
        checkList(purchases);
        return new ResponseEntity<>(
                purchases
                        .stream()
                        .map(DtoConverter::convertPurchase)
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Method to register user.
     * @param user user to register.
     * @param request to get language of header.
     * @return ResponseEntity with result.
     */
    @Timed
    @Requested
    @PostMapping(produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ANONYMOUS')")
    public @ResponseBody ResponseEntity<String> registerUser(@Valid @RequestBody User user,
                                               HttpServletRequest request) {
        if (!checkEntity(userService.findByLogin(user.getLogin()))) {
            return new ResponseEntity<>(messageSource.getMessage("create", new Object[]{},
                    Locale.forLanguageTag(checkLang(request))), HttpStatus.BAD_REQUEST);
        }
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return new ResponseEntity<>(messageSource.getMessage("registration", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + userService.findByLogin(user.getLogin()).getId(),
                HttpStatus.CREATED);
    }

    /**
     * Method to buy giftCertificate.
     * @param giftId to find giftCertificate.
     * @param request to get language of header.
     * @return ResponseEntity with result.
     */
    @Timed
    @Requested
    @PostMapping(value = "/giftCertificates/{giftId}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<String> buyGiftCertificateForUser(@PathVariable("giftId")
                                                                                  Integer giftId,
                                                                          HttpServletRequest request)
            throws FindObjectException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByLogin(((UserDetails) principal).getUsername());
        checkEntityExistence(user);
        GiftCertificate giftCertificate = giftCertificateService.findByCertificateId(giftId);
        checkEntityExistence(giftCertificate);
        purchaseService.create(new Purchase(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")),
                giftCertificate.getPrice(), user.getId(), giftId));
        return new ResponseEntity<>(messageSource.getMessage("buyGift", new Object[]{},
                        Locale.forLanguageTag(checkLang(request)))
                + purchaseService.findPurchaseByUserId(user.getId(), null, null).size(), HttpStatus.CREATED);
    }

    /**
     * Method to delete user by id.
     * @param id to find in db.
     * @param request to get language of header.
     * @return ResponseEntity with result.
     */
    @Timed
    @Requested
    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody ResponseEntity<String> deleteUser(@PathVariable("id") Integer id, HttpServletRequest request)
            throws DeleteConstraintException, FindObjectException {
        checkEntityExistence(userService.findById(id));
        int deleted = userService.delete(id);
        return new ResponseEntity<>(messageSource.getMessage("userDeleted", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + deleted, HttpStatus.OK);
    }

    /**
     * Method to update user.
     * @param id to find in db.
     * @param user element.
     * @param request element.
     * @return ResponseEntity with result.
     */
    @Timed
    @Requested
    @PutMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public @ResponseBody ResponseEntity<String> updateUser(@PathVariable("id") Integer id,
                                                           @RequestBody User user,
                                                           HttpServletRequest request) throws FindObjectException {
        checkEntityExistence(userService.findById(id));
        user.setId(id);
        userService.update(user);
        return new ResponseEntity<>( messageSource.getMessage("userUpdated", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + id, HttpStatus.OK);
    }
}
