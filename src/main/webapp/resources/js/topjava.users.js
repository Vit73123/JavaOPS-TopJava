const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );

    $("input[type=checkbox]").change(function () {
        enable(
            $(this).closest('tr').attr("id"),
            $(this).is(":checked"));
    })
});

function enable(id, checked) {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "enable",
        data: {
            "id": id,
            "enabled": checked
        }
    }).done(function () {
        $(this).closest('tr').attr("data-user-enabled", checked);
    });
}