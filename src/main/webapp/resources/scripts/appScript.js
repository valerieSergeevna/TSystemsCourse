function addForm() {
    let container = document.getElementById("container");

    let inputTypeName = document.createElement("input");
    //let inputType = document.createElement("input");
    let inputType = document.createElement("select");
    let inputPattern = document.createElement("input");
    let inputDose = document.createElement("input");
    //  let inputPeriod = document.createElement("input");
    let inputStartDate = document.createElement("input");
    let inputEndDate = document.createElement("input");

    container.append('Medicine/Procedure name ');
    inputTypeName.type = "text";
    inputTypeName.name = "treatmentName";
    inputTypeName.className = "form-control";
    inputTypeName.required = true;
    container.appendChild(inputTypeName);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Type ');

    let opt1 = document.createElement("option");
    let opt2 = document.createElement("option");

    opt1.value = "medicine";
    opt1.text = "medicine";

    opt2.value = "procedure";
    opt2.text = "procedure";

    inputType.add(opt1);
    inputType.add(opt2);
    inputType.name = "treatmentType";
// inputType.type = "text";

    inputType.className = "form-control";
    inputType.required = true;
    container.appendChild(inputType);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Time Pattern (times/day(week)) ');
    inputPattern.type = "number";
    inputPattern.name = "treatmentPattern";
    inputPattern.className = "form-control";
    inputPattern.required = true;
    inputPattern.min = "1";
    inputPattern.max = "5";
    container.appendChild(inputPattern);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Dose (gr/time)');
    inputDose.type = "number";
    inputDose.step ="0.001";
    inputDose.name = "treatmentDose";
    inputDose.className = "form-control";
    inputDose.required = true;
    inputDose.min = "0";
    container.appendChild(inputDose);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    /*  container.append('Period ');
      inputPeriod.type = "text";
      inputPeriod.name = "treatmentPeriod";
      inputPeriod.className = "form-control";
      container.appendChild(inputPeriod);
      container.appendChild(document.createElement("br"));
      container.appendChild(document.createElement("br"));*/

    container.append('Start date ');
    inputStartDate.type = "date";
    inputStartDate.name = "startDate";
    inputStartDate.className = "form-control datepicker";
    inputStartDate.required = true;
    // inputStartDate.pattern ="\d{4}-\d{2}-\d{2}";
    //  inputStartDate.id="datepicker1";
    container.appendChild(inputStartDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('End date ');
    inputEndDate.type = "date";
    inputEndDate.name = "endDate";
    inputEndDate.className = "form-control datepicker";
    //    inputEndDate.id="datepicker2";
    inputEndDate.required = true;
    // inputEndDate.pattern = "\d{4}-\d{2}-\d{2}";
    container.appendChild(inputEndDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));
}

function doseSelected(a) {

    let label = a.value;

    if (label == 1) {
        document.getElementById("Label1").style.display = 'block';
        document.getElementById("Label2").style.display = 'none';
        document.getElementById("Label3").style.display = 'none';
    } else if (label == 2) {
        document.getElementById("Label1").style.display = 'none';
        document.getElementById("Label2").style.display = 'block';
        document.getElementById("Label3").style.display = 'none';
    }
}

let date = new Date();
let currentMonth = date.getMonth();
let currentDate = date.getDate();
let currentYear = date.getFullYear();

$('body').on('focus', ".datepicker", function () {
    $(this).datepicker({
        minDate: new Date(currentYear, currentMonth, currentDate),
        dateFormat: 'yy-mm-dd'
    });
});


$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})

var logout = function() {
    $.post("/logout", function() {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    })
    return true;
}

$.ajaxSetup({
    beforeSend : function(xhr, settings) {
        if (settings.type == 'POST' || settings.type == 'PUT'
            || settings.type == 'DELETE') {
            if (!(/^http:.*/.test(settings.url) || /^https:.*/
                .test(settings.url))) {
                // Only send the token to relative URLs i.e. locally.
                xhr.setRequestHeader("X-XSRF-TOKEN",
                    Cookies.get('XSRF-TOKEN'));
            }
        }
    }
});

/*
$(function () {
    $("#datepicker1").datepicker();
    $("#datepicker2").datepicker();
})
*/