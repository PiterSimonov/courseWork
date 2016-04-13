$(document).ready(function () {

    $("a#showpopup").click(function (e) {
        e.preventDefault();
        loadPopup();
    });
    $("div#back").click(function () {
        off();
    });
    $("div.close").click(function () {
        off();
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