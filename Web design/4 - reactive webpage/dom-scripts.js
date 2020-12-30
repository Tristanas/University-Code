var patrons = 0;
var names = 2;
var resursoNuoroda = "https://api.myjson.com/bins/1dqx3s";

function checkEmpty(input) {
    if (input.value == '')
        alert(input.name + ' laukas yra tuscias');
}

function checkPositiveInteger(input) {
    var number = parseInt(input.value);
    if (number != input.value || number < 0) {
        alert(`"${input.name}" laukas turi buti sveikas teigiamas skaicius`);
        return;
    }
}

function checkDateCorrect(input) {
    var numbers = (input.value).split('-');
    if (numbers.length < 3) {
        alert(`"${input.name}" lauke data yra blogai parasyta. Formatas: metai-menesis-diena`);
        return;
    }
    // Jei ives metus "02019", bus gauta int 2019. 
    var year = parseInt(numbers[0]),
        month = parseInt(numbers[1]) - 1,
        day = parseInt(numbers[2]);
    if (month > 11 || month < 0)
        alert(`"${input.name}" lauke data yra blogai parasyta. Menesis nera tarp 1 ir 12.`)

    var data = new Date(year, month, day);
    if (data.getDate() != day) {
        alert(`"${input.name}" lauke data yra blogai parasyta. Duotame menesyje nera ${day}-os dienos.`)
    }
}

function filterTable(input) {
    var $$ = jQuery.noConflict();
    var browserRows = $$('#browsers tr');
    var selectedBrowser = input.value;
    for (var i = 2; i < browserRows.length; i++) {
        var shouldHide = browserRows[i].firstElementChild.textContent != selectedBrowser;
        browserRows[i].hidden = shouldHide;
    }
}

function changeParagraphText(input) {
    var name = input.value;
    var newParagraphText = `Welcome, ${name}, to our website!`;
    var $p = $('#name-paragraph').text(newParagraphText);
    //$p[0].textContent = newParagraphText;
    // alert($p); // Object object
}

function addMoreNames() {
    var prefix = 'th';
    switch (names) {
        case 2:
            prefix = 'nd';
            break;
        case 3:
            prefix = 'rd';
            break
    }
    var nameNumber = `${names}-${prefix}`;
    var newNameInput = `<p>${nameNumber} name: <input type="text" name="${nameNumber}-name"><br></p>`;
    $('#names').append(newNameInput);
    names++;
}

function removeLatestName() {
    $('#names p:last').remove();
    if (names > 2)
        names--;
}

function secretChanges(input) {
    // Write red, black, white or #456123 or any similar number to the input to check the effect.
    $('form').css('background-color', input.value);
    /*
    var backgroundColor = '';
    switch (input.value) {
        case 'red':
            backgroundColor = 'red';
            break;
        case 'green':
            backgroundColor = 'green';
            break;
        case 'black':
            backgroundColor = 'black';
            break;
    }
    if (backgroundColor) {
        $('form').css('background-color', backgroundColor);
    }
    */
}




function addPatron() {
    var name = $('#patrons-form #first-name').val();
    var lastName = $('#patrons-form #last-name').val();

    $.ajax({
        url: resursoNuoroda,
        type: "PUT",
        data: `{"firstName":"${name}", "lastName":"${lastName}"}`,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            addPatronToTable(name, lastName);
            congratulateLatestPatron();
            //alert(textStatus + " - text status \n" + jqXHR + " - jqXHR");
        }
    });    
}

function addPatronToTable(name, lastName) {
    var newTableRow = `<tr><td>${++patrons}</td><td>${name}</td><td>${lastName}</td></tr>`;
    $('#patrons').append(newTableRow);
}

function congratulateLatestPatron() {
    $.get(resursoNuoroda, function (patron, textStatus, jqXHR) {
        //alert(patron.toString());
        updateLatestPatron(patron);
    });
}

function updateLatestPatron(patron) {
    if (patron && patron.firstName && patron.lastName)
        $('#latest-patron').text(patron.firstName + ' ' + patron.lastName);
}