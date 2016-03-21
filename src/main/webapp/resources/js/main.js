$(document).ready(function () {
    $('.date').prop("min", function () {
        return new Date().toJSON().split('T')[0];
    });
    //$("#form-booking").submit(function (e) {
    //    e.preventDefault();
    //    var userRole = $('input#userRole').val();
    //    if (userRole == "HotelOwner" || userRole == 'CLIENT') {
    //        $(":submit").attr("disabled", true);
    //        $.ajax({
    //            url: "/check-date",
    //            type: "GET",
    //            data: $("#form-booking").serialize(),
    //            async: false,
    //            success: function (data) {
    //                if (data) {
    //                    $("#is-free").html("<img class='image-ok' src='/resources/images/ok.gif'/>")
    //                } else {
    //                    $("#is-free").html("<img class='image-ok' src='/resources/images/error.jpg'/>")
    //                }
    //            }
    //        });
    //        $(":submit").removeAttr("disabled");
    //    } else {
    //        loadPopup();
    //    }
    //});

    $('form#choice-room').submit(function (e) {
        var userRole;
        $.ajax({
            url: "/get-user",
            type: "GET",
            async: false,
            success: function (responce) {
                userRole = responce;
            }
        });
        if (userRole == "HotelOwner" || userRole == 'CLIENT') {
            $(":submit").attr("disabled", true);
        } else {
            loadPopup();
            return false;
        }
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

    $("a#showpopup").click(function () {
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
                success: function (data) {
                    if (data == 'logged') {
                        off();
                        $('.user-bar').html('<a class="menuLink" href="/profile">Profile</a>' +
                            '<a class="menuLink" href="/logout">Logout</a>')
                    } else {
                        $("#error-box").text(data);
                    }
                }
            });
        }
    });

    $('#country_id').one('click', function () {
        $.get('/search/country', function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#country_id').html(option);
        })
    }).on("change", function () {
        $('#city_id').removeAttr("disabled");
        var data = $('#country_id').val();
        $.get('/search/city', {country_id: data}, function (result) {
            var option = '';
            $.each(result, function (key, data) {
                option += '<option value="' + key + '">' + data + '</option>'
            });
            $('#city_id').html(option);
        })
    })
});