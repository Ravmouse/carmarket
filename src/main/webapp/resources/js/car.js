/**
 * Имя файла.
 */
let fileName;

/**
 * После нажатия на кнопку "Войти" производится проверка на то, чтобы все поля были заполнены.
 * Далее в лок.хранилище кладется имя пользователя.
 * Далее ajax-запрос на проверку, есть ли такой пользователь и пароль.
 * Если есть, то ответ == true и переход на страницу main.
 */
$('#btnLogin').on('click', function () {
    if (validate()) {
        let name = $('#login').val();
        localStorage.setItem("name", name);
        $.ajax({
            url: './log',
            method: 'POST',
            dataType: 'text',
            data: JSON.stringify({name : name, password : $('#password').val()}),
            success: function (answer) {
                if (answer) {
                    window.location.href = 'main.html';
                } else {
                    alert('Неправильный логин и(или) пароль!');
                }
            }
        });
    }
});

/**
 * Нажатие на кнопку "Войти анонимно" очищает лок.хранилище и переводит на страницу main.
 * Т.о. имя пользователя будет null, и все объявления будут неактивными.
 */
$('#btnAnon').on('click', function () {
    localStorage.clear();
    window.location.href = 'main.html';
});

/**
 * После получения ответа от ajax-запроса каждое значение JSON (это offer) добавляется в таблицу.
 * Проверяется, если у offer sold == true, то добавленный checkbox делается checked.
 * Также меняется цвет td таблицы там, где checkbox == checked.
 * Из лок.хранилища достается имя пользователя, а все офферы, непринадлежащие этому пользователям, становятся неактивными.
 */
function getOffers(url, flag, method, dataValue) {
    let name = localStorage.getItem("name");
    $.ajax({
        url: url,
        method: method,
        dataType: 'json',
        data: dataValue,
        success: function (data) {
            $.each(data, function (k, v) {
                let elem;
                $('table tr:last').append(
                    '<td class="myCell">' +
                    '<img src="resources/img/' + v.imgName + '">' + '<br>' +
                    '<label class="color_text">Марка:&nbsp;</label><label>' + v.car.brand.name + '</label><br>' +
                    '<label class="color_text">Модель:&nbsp;</label><label>' + v.car.model.name + '</label><br>' +
                    '<label class="color_text">Год:&nbsp;</label><label>' + v.car.year + '</label><br>' +
                    '<label class="color_text">Тип кузова:&nbsp;</label><label>' + v.car.carBody.name + '</label><br>' +
                    '<label class="color_text">Коробка передач:&nbsp;</label><label>' + v.car.transmission.name + '</label><br>' +
                    '<label class="color_text">Тип двигателя:&nbsp;</label><label>' + v.car.engine.name + '</label><br>' +
                    '<label class="color_text">Продано:&nbsp;</label><input class="dynCheck" type="checkbox" id="ch' + k + '"><br>' +
                    '<label class="color_text">Дата объявления:&nbsp;</label><label>' + v.createDate + '</label><br>' +
                    '</td>');
                if (v.sold) {
                    elem = $('#ch' + k);
                    elem.prop("checked", true);
                    elem.closest("td").css("background-color", "#f5f5f5");
                }
                if (v.user.name !== name) {
                    elem = $('#ch' + k);
                    elem.prop('disabled', true);
                }
            });
        }
    });
    if (flag) {
        $.ajax({
            url: './fields',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                let brand = $('#cmbxBrnd');
                brand.empty();
                brand.append('<option value=\"\" disabled selected>--Выберите марку--</option>');
                $.each(data.brands, function (k, v) {
                    $('#cmbxBrnd').append('<option value="' + (k + 1) + '">' + v + '</option>');
                });
            },
        })
    }
}

/**
 * Клик по checkbox "Показать за последний день".
 * По классу myCell очищаются все строки таблицы.
 * Посылается ajax-запрос на сервлет, в котором получаются офферы только за последний день.
 */
$('#lstDay').on('change', function () {
    $('.myCell').remove();
    getOffers('./day', false, 'GET');
});

/**
 * Выделенное в comboBox, затем - значение выделенного.
 * По классу myCell очищаются все строки таблицы.
 * Посылается ajax-запрос со значением выбранной машины в comboBox на сервлет, в котором получаются офферы.
 */
$('#cmbxBrnd').on('change', function () {
    let optSelected = $('option:selected', this);
    let valSelected = optSelected.val();
    $('.myCell').remove();
    getOffers('./brand', false, 'POST', JSON.stringify({brand : valSelected}));
});

/**
 * Переход со страницы main на страницу add.
 */
$('#btn_next').on("click", function () {
    window.location.href = 'add.html';
});

/**
 * Добавление каждому <select> соответствующего значения из JSON.
 */
function fillAllElements() {
    $.ajax({
        url: './fields',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#brand').append('<option value=\"\" disabled selected>--Выберите марку--</option>');
            $('#model').append('<option value=\"\" disabled selected>--Выберите модель--</option>');
            $('#body').append('<option value=\"\" disabled selected>--Выберите тип кузова--</option>');
            $('#trans').append('<option value=\"\" disabled selected>--Выберите коробку передач--</option>');
            $('#engine').append('<option value=\"\" disabled selected>--Выберите тип двигателя--</option>');

            $.each(data.brands, function (k, v) {
                $('#brand').append('<option value="' + (k + 1) + '">' + v + '</option>');
            });

            $.each(data.models, function (k, v) {
                $('#model').append('<option value="' + (k + 1) + '">' + v + '</option>');
            });

            $.each(data.body, function (k, v) {
                $('#body').append('<option value="' + (k + 1) + '">' + v + '</option>');
            });

            $.each(data.trans, function (k, v) {
                $('#trans').append('<option value="' + (k + 1) + '">' + v + '</option>');
            });

            $.each(data.engine, function (k, v) {
                $('#engine').append('<option value="' + (k + 1) + '">' + v + '</option>');
            });

        },
    })
}

/**
 * При нажатии на кнопку "Загрузить" сохраняется имя файла, который был выбран в input type=file без названия директории.
 * Далее отправляется ajax-запрос на сохранение файла на сервере.
 * Если файл был успешно сохранен, то в <div class="... left"> добавляется <img> с именем файла из input type=file
 * (причем сам файл с этим именем уже лежит на сервере).
 */
$('#btnUpload').on("click", function () {
    fileName = $('input[type=file]').val().split('\\').pop();
    let form = $('#upldFrm')[0];
    let data = new FormData(form);

    $.ajax({
        url: './upload',
        method: 'POST',
        encType : "multipart/form-data",
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            $('.left').append('<img src="resources/img/' + fileName + '">');
        }
    });
});

/**
 * Кнопка "Создать объявление".
 */
$("#btnCreate").on('click', function () {
    $.ajax({
        url: './add',
        method: 'POST',
        data: JSON.stringify({brand : $('#brand').val(), model : $('#model').val(), year : $('#year').val(),
                              carBody : $('#body').val(), transmission : $('#trans').val(), engine : $('#engine').val(),
                              imgName : fileName}),
        success: function (data) {
            window.location.href = 'main.html';
        }
    });
});

/**
 * Проверка на то, чтобы все поля были заполнены.
 * @returns {boolean}
 */
function validate() {
    let rsl = true;
    let elements = document.querySelectorAll('input');
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].value === "") {
            rsl = false;
            alert('Вы не заполнили поле: ' + $(elements[i]).attr('name'));
            break;
        }
    }
    return rsl;
}