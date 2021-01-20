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
    container.appendChild(inputType);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Time Pattern ');
    inputPattern.type = "text";
    inputPattern.name = "treatmentPattern";
    inputPattern.className = "form-control";
    container.appendChild(inputPattern);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Dose ');
    inputDose.type = "text";
    inputDose.name = "treatmentDose";
    inputDose.className = "form-control";
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
    inputStartDate.type = "text";
    inputStartDate.name = "startDate";
    inputStartDate.className = "form-control datepicker";
    //  inputStartDate.id="datepicker1";
    container.appendChild(inputStartDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('End date ');
    inputEndDate.type = "text";
    inputEndDate.name = "endDate";
    inputEndDate.className = "form-control datepicker";
    //    inputEndDate.id="datepicker2";
    container.appendChild(inputEndDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));
}

$('body').on('focus', ".datepicker", function () {
    $(this).datepicker();
});

/*
$(function () {
    $("#datepicker1").datepicker();
    $("#datepicker2").datepicker();
})
*/