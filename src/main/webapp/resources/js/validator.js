$(document).ready(function () {
    $.validator.addMethod("phone", function (number, element) {
        number = number.replace(/\s+/g, "");
        return this.optional(element) || number.length > 9 &&
            number.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/);
    }, "Please specify a valid phone number");
    $('form#registerForm').validate({
        rules: {
            login: {
                required: true,
                minlength: 3,
                maxlength: 25,
                remote: {
                    url: '/check-login',
                    type: "post"
                }
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 25
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: '/check-email',
                    type: "post"
                }
            },
            phone: {
                required: true,
                phone: true
            }
        },
        messages: {
            "login": {
                required: "login is required!",
                minlength: "login must be at least 3 characters long",
                remote: "Login is already taken"
            },
            "password": {
                required: "Please enter a password",
                minlength: "Password must be at least 6 characters long"
            },
            "email": {
                remote: "E-mail is already taken"
            }
        }
    });
    $('form#edit-user-form').validate({
        rules: {
            phone: {
                required: true,
                phone: true
            }
        }
    });
});