import * as Yup from 'yup';

export const LoginSchema = Yup.object().shape({
    username: Yup.string()
        .min(3, "Username field length must not be less than 3 characters!")
        .max(30, "Username field length must not be greater than 30 characters!")
        .required("Username is required"),
    password: Yup.string()
        .min(4, "Username field length must not be less than 4 characters!")
        .max(30, "Username field length must not be greater than 30 characters!")
        .required("Password is required")
  });

export const CertificateSchema = Yup.object().shape({
    title: Yup.string()
        .min(6, "Title field must not be less than 6!")
        .max(30, "Title field must not be greater than 30 characters!")
        .required("Title is required"),
    description: Yup.string()
        .min(12, "Description field must not be less than 12")
        .max(1000, "Description field must not be greater than 1000 characters!")
        .required("Description is required")
  });