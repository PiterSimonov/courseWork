$(document).ready(function () {
    $.validator.addMethod("phone", function (number, element) {
        number = number.replace(/\s+/g, "");
        return this.optional(element) || number.length > 9 &&
            number.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/);
    }, "Please specify a valid phone number");
    $.validator.addMethod('fileSize', function (value, element, param) {
        return this.optional(element) || (element.files[0].size <= param)
    }, "File must be less than 3MB");
    $.validator.addMethod("accept", function (value, element, param) {
        var typeParam = typeof param === "string" ? param.replace(/\s/g, '').replace(/,/g, '|') : "image/*",
            optionalValue = this.optional(element),
            i, file;
        if (optionalValue) {
            return optionalValue;
        }
        if ($(element).attr("type") === "file") {
            typeParam = typeParam.replace(/\*/g, ".*");
            if (element.files && element.files.length) {
                for (i = 0; i < element.files.length; i++) {
                    file = element.files[i];
                    if (!file.type.match(new RegExp(".?(" + typeParam + ")$", "i"))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }, "Please enter a file with a valid extension.");
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

    $('form#add-hotel, #add-room').validate({
        rules: {
            imageFile: {
                fileSize: 3145728,
                accept: "png|jpe?g|gif"
            },
            country_id: {
                required: true,
                min: 1
            }
        },
        message: {
            imageFile: {fileSize: "File must be JPG, GIF or PNG, less than 3MB"},
            country_id: {min: "Please select a country"}
        }
    })
});