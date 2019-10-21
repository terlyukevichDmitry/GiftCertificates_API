package com.epam.esm.dto;

import com.epam.esm.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * This class we use to convert entity to dtoEntity and getting with dto to client.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Component
public class DtoConverter {
    /**
     * object.
     */
    private static ModelMapper modelMapper;

    /**
     * Constructor to init modelMapper with the help of a spring.
     * @param modelMapper object.
     */
    private DtoConverter(ModelMapper modelMapper) {
        DtoConverter.modelMapper = modelMapper;
    }

    /**
     * Method to convert purchase to dto form.
     * @param purchase to convert.
     * @return converted purchase.
     */
    public static Entity convertPurchase(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDto.class);
    }

    /**
     * Method to convert user to dto form.
     * @param user to convert.
     * @return converted user.
     */
    public static Entity convertUser(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Method to convert userDto to dto form.
     * @param userDto to convert.
     * @return converted userDto.
     */
    public static Entity convertUserDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    /**
     * Method to convert giftCertificateDto to dto form.
     * @param giftCertificateDto to convert.
     * @return converted giftCertificateDto.
     */
    public static Entity convertGiftCertificateDto(GiftCertificateDto giftCertificateDto) {
        return modelMapper.map(giftCertificateDto, GiftCertificate.class);
    }

    /**
     * Method to convert giftCertificate to dto form.
     * @param giftCertificate to convert.
     * @return converted giftCertificate.
     */
    public static Entity convertGiftCertificate(GiftCertificate giftCertificate) {
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    /**
     * Method to convert tagDto to dto form.
     * @param tagDto to convert.
     * @return converted tagDto.
     */
    public static Entity convertTagDto(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    /**
     * Method to convert tag to dto form.
     * @param tag to convert.
     * @return converted tag.
     */
    public static Entity convertTag(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
