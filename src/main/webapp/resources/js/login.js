$(document).ready(function () {

    $("a#showpopup").click(function (e) {
        e.preventDefault();
        loadPopup();
    });
    $("div#back").click(function () {
        off();
        offRegister();
    });
    $("div.close").click(function () {
        off();
        offRegister();
    });
    $("#loginForm").submit(function (e) {
        e.preventDefault();
        var loginInput = $('input#login').val();
        if (loginInput.length < 3) {
            $("#error-box").text("Login length must be > 3");
        } else if ($('input#password').val().length < 6) {
            $("#error-box").text("Password length must be > 6");
        }
        else {
            $.ajax({
                url: '/check-user',
                type: 'POST',
                data: $("#loginForm").serialize(),
                success: function (response) {
                    if (response == 'logged') {
                        off();
                        $('.user-bar').html('<a class="menuLink" href="/profile">Profile</a>' +
                            '<a class="menuLink" href="/logout">Logout</a>')
                    } else {
                        $("#error-box").text(response);
                    }
                }
            });
        }
    });

    $("#registration").click(function (e) {
        e.preventDefault();
        off();
        loadRegister();

    });

    $("#signIn").click(function (e) {
        e.preventDefault();
        off();
        loadRegister();

    });

    $('form#registerForm').submit(function (e) {
        e.preventDefault();
        var $thisForm = $(this);
        if (!$thisForm.valid()) {
            return false;
        }
        var $submit = $thisForm.find(":submit");
        $submit.attr("disabled", true);
        $.ajax({
            url: "/registration",
            type: "POST",
            dataType: "html",
            data: $("#registerForm").serialize(),
            success: function (data) {
                offRegister();
                $('.user-bar').html('<a class="menuLink" href="/profile">Profile</a>' +
                    '<a class="menuLink" href="/logout">Logout</a>')
            },
            error: function () {
                $("#error-reg").text("Error registration. Try again!");
                $submit.attr("disabled", false);
            }
        });
    })
});

var on = 0;

function loadPopup() {
    if (on == 0) {
        $("#back").css("opacity", "0.6");
        $("#popup").slideDown(500);
        $("#back").fadeIn(1500);
        on = 1;
    }
}

function off() {
    if (on == 1) {
        $("#popup").slideUp("fast");
        $("#back").fadeOut("fast");
        on = 0;
    }
}

function loadRegister() {
    if (on == 0) {
        $("#back").css("opacity", "0.6");
        $("#register").slideDown(500);
        $("#back").fadeIn(1500);
        on = 1;
    }
}

function offRegister() {

    $("#register").slideUp("fast");
    $("#back").fadeOut("fast");
    on = 0;

}