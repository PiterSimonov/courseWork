$(document).ready(function () {
    var d = new Date();
    var today = dateToString(d);
    var nextDay = dateToString(d, 1);
    var maxDays = dateToString(d, 28);
    var $fromDate = $('#fromDate');
    var $toDate = $('#toDate');
    $fromDate.prop("value", today);
    $fromDate.prop("min", today);
    $toDate.prop("value", nextDay);
    $toDate.prop("min", nextDay);
    $toDate.prop("max", maxDays);


    $("#fromDate").change(function () {
        var x = fromDate.value;
        var d = new Date(Date.parse(x));
        var nextDay = dateToString(d, 1);
        var maxDays = dateToString(d, 28);
        var $toDate = $("#toDate");
        $toDate.prop("max", maxDays);
        $toDate.prop("value", nextDay);
        $toDate.prop("min", nextDay);
    });

    function dateToString(d, num) {
        if (num === undefined) {
            return d.toJSON().split('T')[0];
        } else {
            var nextDay = new Date(d.valueOf() + 24 * 60 * 60 * 1000 * num);
            return nextDay.toJSON().split('T')[0];
        }
    }

    $("#city").keyup(function () {
        var $city = $("#cityList");
        $city.html("");
        $city.css("display", "none");
        if (city.value.length >= 3) {
            var countryId = $("#countryId").attr("value");
            $.ajax({
                url: "/search/city/" + city.value + "/" + countryId,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        data.forEach(function (i) {
                            var li = li = document.createElement('li');
                            li.appendChild(document.createTextNode(i.name));
                            li.onclick = function(){
                                city.value = i.name;
                                $("#cityId").attr("value", i.id);
                                $("#hotel").val("");
                                $("#hotelId").attr("value", 0);
                                $("#hotelList").html("");
                                $city.html("");
                                $city.css("display", "none");
                            };
                            $city.append(li);
                            $city.css("display", "block");
                        })
                    }
                });
        }
    });

    $("#country").keyup(function () {
        var $country = $("#countryList");
        $country.html("");
        $country.css("display", "none");

        if (country.value.length >= 3) {
            $.ajax({
                url: "/search/country/" + country.value,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    data.forEach(function (i) {
                        var li = li = document.createElement('li');
                        li.appendChild(document.createTextNode(i.name));
                        li.onclick = function () {
                            country.value = i.name;
                            $("#countryId").attr("value", i.id);
                            $country.html("");
                            $("#city").val("");
                            $("#cityId").attr("value", 0);
                            $("#cityList").html("");
                            $("#hotel").val("");
                            $("#hotelId").attr("value", 0);
                            $("#hotelList").html("");
                            $country.css("display", "none");
                        };
                        $country.append(li);
                        $country.css("display", "block");
                    })
                }
            });
        }
    });

    $("#hotel").keyup(function () {
        var $hotel = $("#hotelList");
        $hotel.html("");
        $hotel.css("display", "none");
        if (hotel.value.length >= 3) {
            var cityId = $("#cityId").attr("value");
            var countryId = $("#countryId").attr("value");
            $.ajax({
                url: "/search/hotel/" + hotel.value + "/" + cityId + "/" + countryId,
                type: "GET",
                dataType: "json",
                success: function (data) {

                    data.forEach(function (i) {
                        var li = li = document.createElement('li');
                        li.appendChild(document.createTextNode(i.name));
                        li.onclick = function () {
                            hotel.value = i.name;
                            $("#hotelId").attr("value", i.id);
                            $hotel.html("");
                            $hotel.css("display", "none");
                        };
                        $hotel.append(li);
                        $hotel.css("display", "block");
                    })
                }
            });
        }
    });

    $("#country").change(function () {
        $("#countryId").attr("value", 0);
    });

    $("#city").change(function () {
        $("#cityId").attr("value", 0);
    });

    $("#hotel").change(function () {
        $("#hotelId").attr("value", 0);
    });

    $("#addRoom").click(function () {
        var number = document.createElement("input");
        number.type = "number";
        number.min = 1;
        number.value = 1;
        number.max = 8;
        $("#rooms").append(number);


    });

    $("body").click(function (e) {
        if (e.target.id !== "countryList" && e.target.id !== "country") {
            $("#countryList").css("display", "none");
        }
        if (e.target.id !== "cityList" && e.target.id !== "city") {
            $("#cityList").css("display", "none");
        }
        if (e.target.id !== "hotelList" && e.target.id !== "hotel") {
            $("#hotelList").css("display", "none");
        }
    });

    $("#country").focus(function () {
        if ($("#countryList").children().length > 0) {
            $("#countryList").css("display", "block");
        }
    });

    $("#city").focus(function () {
        if ($("#cityList").children().length > 0) {
            $("#cityList").css("display", "block");
        }
    });

    $("#hotel").focus(function () {
        if ($("#hotelList").children().length > 0) {
            $("#hotelList").css("display", "block");
        }
    });

    $("#search").click(function () {
        var seats = {};
        var elements = $("#rooms").children();
        $.each(elements, function () {
            var x = this.value;
            if (x in seats) {
                var val = seats[this.value];
                seats[this.value] = val + 1;

            } else {
                seats[this.value] = 1;
            }
        });

        var param = {};
        param.countryId = $("#countryId").val();
        param.cityId = $("#cityId").val();
        param.hotelId = $("#hotelId").val();
        param.startDate = $("#fromDate").val();
        param.endDate = $("#toDate").val();
        param.seats = seats;
        param.firstResult = 1;
        param.limit = 5;

        var jsonData = JSON.stringify(param);
        $.ajax({
            url: "/search/test",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: jsonData,
            success: function () {
                document.location.href = "/search/hotels"
            }
        });

    });

    var firstResult = 1;

    $("#more").on("click", (function () {
        firstResult += 5;
        $.ajax({
            url: "/search/nextHotels/" + firstResult,
            type: "GET",
            success: function (data) {
                $("#hotelsList").append(data);
            }
        });
    }));





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

    $("#form-booking").submit(function (e) {
        e.preventDefault();
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
            $.ajax({
                url: "search/check-date", //TODO create order with one booking... and Controller for handling this
                type: "GET",
                data: $("#form-booking").serialize(),
                success: function (data) {
                    if (data) {

                    } else {
                        $("#is-free").html("<img class='image-ok' src='/resources/images/error.jpg'/> Sorry, this roomis booking for thi dates")
                    }
                }
            });
            $(":submit").removeAttr("disabled");
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