function addForm() {
    // Number of inputs to create
    //var number = document.getElementById("count").value;
    // Container <div> where dynamic content will be placed
    let container = document.getElementById("container");
    // Clear previous contents of the container
    //  while (container.hasChildNodes()) {
    //      container.removeChild(container.lastChild);
    //  }
    // for (i=0;i<number;i++){
    // Append a node with a random text
    // container.appendChild(document.createTextNode("treatmentName"));
    // Create an <input> element, set its type and name attributes

    let inputTypeName = document.createElement("input");
    let inputType = document.createElement("input");
    let inputPattern = document.createElement("input");
    let inputDose = document.createElement("input");
    let inputPeriod = document.createElement("input");

    inputTypeName.type = "text";
    inputTypeName.name = "treatmentName";
    container.appendChild(inputTypeName);
    container.appendChild(document.createElement("br"));

    inputType.type = "text";
    inputType.name = "treatmentType";
    container.appendChild(inputType);
    container.appendChild(document.createElement("br"));

    inputPattern.type = "text";
    inputPattern.name = "treatmentPattern";
    container.appendChild(inputPattern);
    container.appendChild(document.createElement("br"));

    inputDose.type = "text";
    inputDose.name = "treatmentDose";
    container.appendChild(inputDose);
    container.appendChild(document.createElement("br"));

    inputPeriod.type = "text";
    inputPeriod.name = "treatmentPeriod";
    container.appendChild(inputPeriod);
    container.appendChild(document.createElement("br"));
}

function rmline(q) {
    s = document.getElementById('table').innerHTML;
    s = s.replace(/[\r\n]/g, '');
    re = new RegExp('<tr id="?newline"? nomer="?\\[' + q + '.*?<\\/tr>', 'gi');
    // это регулярное выражение позволяет выделить строку таблицы с заданным номером
    s = s.replace(re, '');
    // заменяем её на пустое место
    document.getElementById('table').innerHTML = s;
    return false;
}