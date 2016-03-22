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


    $("#fromDate").change(function() {
            var x = fromDate.value;
            var d = new Date(Date.parse(x));
            var nextDay = dateToString(d, 1);
            var maxDays = dateToString(d, 28);
            var $toDate = $("#toDate");
            $toDate.prop("max", maxDays);
            $toDate.prop("value", nextDay);
            $toDate.prop("min", nextDay);
    });

    function dateToString(d, num){
        if(num === undefined){
            return d.toJSON().split('T')[0];
        }else{
            var nextDay = new Date(d.valueOf()+24*60*60*1000*num);
            return nextDay.toJSON().split('T')[0];
        }
    }

    $("#city").keyup(function() {
        var $city = $("#cityList");
        $city.html("");
        $city.css("display", "none");
        if (city.value.length >= 3){
            var countryId = $("#countryId").attr("value");
            if (countryId === undefined) {
                countryId = 0;
            }
            $.ajax({
                url: "/city/" + city.value + "/" + countryId,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        data.forEach(function (i) {
                            var li = li = document.createElement('li');
                            li.appendChild(document.createTextNode(i.name));
                            li.onclick = function(){
                                city.value = i.name;
                                $("#cityId").attr("value", i.id);
                                $city.html("");
                                $city.css("display", "none");
                            };
                            $city.append(li);
                            $city.css("display", "block");
                        })
                    },
                });
        }
    })
//TODO удаление id.value после стирания страны, города, отеля
//TODO сворачивание списка при потере фокуса


    $("#country").keyup(function () {
        var $country = $("#countryList");
        $country.html("");
        $country.css("display", "none");
        if (country.value.length >= 3) {

            $.ajax({
                url: "/country/" + country.value,
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
                            $country.css("display", "none");
                        };
                        $country.append(li);
                        $country.css("display", "block");
                    })
                },
            });
        }
    })

    $("#hotel").keyup(function () {
        var $hotel = $("#hotelList");
        $hotel.html("");
        $hotel.css("display", "none");
        if (hotel.value.length >= 3) {
            var cityId = $("#cityId").attr("value");
            if (cityId === undefined) {
                cityId = 0;
            }
            var countryId = $("#countryId").attr("value");
            if (countryId === undefined) {
                countryId = 0;
            }
            $.ajax({
                url: "/hotel/" + hotel.value + "/" + cityId + "/" + countryId,
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
                },
            });
        }
    })

    $("#addRoom").click(function () {
        var number = document.createElement("input");
        number.type = "number";
        $("#addRoom").before(number);

    })


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


    var inProgress = false;
    var startFrom = 5;

    $(window).scroll(function () {
        /* Если высота окна + высота прокрутки больше или равны высоте всего документа
         и ajax-запрос в настоящий момент не выполняется, то запускаем ajax-запрос */
        if ($(window).scrollTop() + $(window).height() >= $(document).height() + 250 && !inProgress) {
            alert("INSIDE IF");
            var data = {};
            data["lastRoom"] = startFrom;
            $.ajax({
                url: '/search/roomsNext',
                type: 'POST',
                data: data,
                beforeSend: function () {
                    inProgress = true;
                }
            }).done(function (data) {
                alert(data);
                data = jQuery.parseJSON(data);

                /* Если массив не пуст  */
                if (data.length > 0) {
                    /* Делаем проход по каждому результату, оказвашемуся в массиве,
                     где в index попадает индекс текущего элемента массива, а в data - сама статья */
                    $.each(data, function (index, data) {

                        /* Отбираем по идентификатору блок со статьями и дозаполняем его новыми данными */
                        $("#articles").append("<p><b>" + data.title + "</b><br />" + data.text + "</p>");

                    });
                    inProgress = false;
                    startFrom += 10;
                }
            });
        }
    });


});