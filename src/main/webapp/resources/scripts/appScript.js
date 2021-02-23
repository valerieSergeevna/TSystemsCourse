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
    inputTypeName.id = "treatmentName";
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
    inputType.id = "type";
    inputType.onchange = function () {
        if (document.getElementById('type').value === "procedure") {
            document.getElementById('dose').hidden = true;
            document.getElementById('dose').required = false;
        } else {
            document.getElementById('dose').hidden = false;
            document.getElementById('dose').required = true;
        }
    }

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
    inputPattern.id = 'pattern';
    container.appendChild(inputPattern);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Dose (gr/time)');
    inputDose.type = "number";
    inputDose.step = "0.001";
    inputDose.name = "treatmentDose";
    inputDose.className = "form-control";
    inputDose.required = true;
    inputDose.min = "0";
    inputDose.id = 'dose';
    container.appendChild(inputDose);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('Start date ');
    inputStartDate.type = "date";
    inputStartDate.name = "startDate";
    inputStartDate.className = "form-control datepicker";
    inputStartDate.required = true;
    // inputStartDate.pattern ="\d{4}-\d{2}-\d{2}";
    //  inputStartDate.id="datepicker1";
    inputStartDate.id = "startDate";
    container.appendChild(inputStartDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    container.append('End date ');
    inputEndDate.type = "date";
    inputEndDate.name = "endDate";
    inputEndDate.className = "form-control datepicker";
    //    inputEndDate.id="datepicker2";
    inputEndDate.required = true;
    inputEndDate.id = "endDate";
    // inputEndDate.pattern = "\d{4}-\d{2}-\d{2}";
    container.appendChild(inputEndDate);
    container.appendChild(document.createElement("br"));
    container.appendChild(document.createElement("br"));

    document.getElementById('newTreatmentButton').hidden = true;
    document.getElementById('removeTreatmentButton').hidden = false;
}

function removeForm() {

    let element = document.getElementById("container");
    element.innerHTML = "";
    document.getElementById('newTreatmentButton').hidden = false;
    document.getElementById('removeTreatmentButton').hidden = true;
}

function showOrHideBinForm(flag) {

    let element = document.getElementById("binListId");
    if (flag){
        element.hidden = false;
    }else {
        element.hidden = true;
    }
    document.getElementById('showBinButton').hidden = flag;
    document.getElementById('hideBinButton').hidden = !flag;

}


function typeSelected(id) {
    if (document.getElementById('type' + id).value === "procedure") {
        document.getElementById('dose' + id).hidden = true;
        document.getElementById('dose' + id).required = false;
    } else {
        document.getElementById('dose' + id).hidden = false;
        document.getElementById('dose' + id).required = true;
    }
}

$('#registrationButton').click(function (e) {
    e.preventDefault(); //to prevent standard click event
    $('#registration').slideToggle(300);
});


$(document).ready(function () {
    $('treatmentType').click(function () {
        var clickId = $(this).prop('id');
        let id = parseInt(clickId.match(/\d+/));
        typeSelected(id);
    });
});

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

var logout = function () {
    $.post("/logout", function () {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    })
    return true;
}
//
// function showToday() {
//     debugger;
//     $.ajax({
//         type: "GET",
//         url: "nurse/updateAjaxStatus",
//         data: { userId: Id },
//         contentType: "application/json;charset=utf-8",
//         dataType: "json",
//             success: function (data) {
//                 $.each(data, function (index) {
//                     $('#myTable').append("<tr><td>" + data[index].id + "</td><td>" + data[index].name + "</td></tr>");
//                 });
//
//             },
//
//         error: function (xhr, ajaxOptions, thrownError) {
//
//             alert('Failed to retrieve books.');
//
//         }
//     });
//
// }

function filter() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("form3Example1");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function filterUsers(id, column) {
    var input, filter, table, tr, td, i;
    input = document.getElementById(id);
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[column];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function typeFilter() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("typeID");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[3];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}


function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("myTable");
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchcount++;
        } else {
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

class airSlider {
    constructor(e) {
        //Element Description
        this.slider = document.querySelector('.air-slider');
        this.slider.children[0].classList.toggle('active-slide');
        //Slider Length
        this.length = document.querySelectorAll('.slide').length;
        //Sizes
        if (e.width == undefined) {
            e.width = '100%';
        }
        if (e.height == undefined) {
            e.height = '1000px';
        }
        this.slider.style.maxWidth = e.width;
        this.slider.style.height = e.height;
        //Constrols
        var controls = document.createElement('div');
        controls.className = 'controls';
        controls.innerHTML = '<button id="prev"><</button><button id="next">></button>';
        this.slider.appendChild(controls);
        //Controls Listeners
        document.querySelector('#prev').addEventListener('click', function () {
            slider.prev();
        });
        document.querySelector('#next').addEventListener('click', function () {
            slider.next();
        });
        //AutoPlay
        if (e.autoPlay == true) {
            this.autoPlayTime = e.autoPlayTime;
            if (this.autoPlayTime == undefined) {
                this.autoPlayTime = 5000;
            }
            setInterval(this.autoPlay, this.autoPlayTime);
        }
    }

    prev() {
        var currentSlide = document.querySelector('.active-slide');
        var prevSlide = document.querySelector('.active-slide').previousElementSibling;
        if (prevSlide == undefined) {
            prevSlide = this.slider.children[this.length - 1];
        }
        currentSlide.className = 'slide';
        prevSlide.classList = 'slide active-slide'
    }

    next() {
        var currentSlide = document.querySelector('.active-slide');
        var nextSlide = document.querySelector('.active-slide').nextElementSibling;
        if (nextSlide.className == 'controls') {
            nextSlide = this.slider.children[0];
        }
        currentSlide.className = 'slide';
        nextSlide.classList = 'slide active-slide fadeIn'
    }

    autoPlay() {
        slider.next();
    }
}

var slider = new airSlider({
    autoPlay: true,
    width: '100%',
    height: '1000px'
});
