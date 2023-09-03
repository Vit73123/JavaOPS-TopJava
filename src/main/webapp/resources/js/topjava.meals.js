const mealAjaxUrl = "profile/meals/";
let filterForm = $('#filterForm');

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function setFilter() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "filter",
        data: $('#filterForm').serialize()
    }).done(function () {
        updateTable();
    });
}

function clearFilter() {
    $('#filterForm')[0].reset();
    setFilter();
}
