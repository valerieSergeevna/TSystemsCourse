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

    container.append('Time Pattern (times/day) ');
    inputPattern.type = "text";
    inputPattern.name = "treatmentPattern";
    inputPattern.className = "form-control";
    inputPattern.required = true;
    inputPattern.min = "1";
    container.appendChild(inputPattern);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Dose (gr/time)');
    inputDose.type = "text";
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

$('body').on('focus', ".datepicker", function () {
    $(this).datepicker({
        dateFormat: 'yy-mm-dd'
    });
});

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})


/*
$(function () {
    $("#datepicker1").datepicker();
    $("#datepicker2").datepicker();
})
*/