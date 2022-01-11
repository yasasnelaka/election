function changeDistrict(provinceId,dependId) {
    $.ajax({
        type: 'POST',
        url: '/get-districts-by-province',
        data: JSON.stringify({"id": provinceId}),
        success: function (data, status, xhr) {
            setDetails(data,dependId);
        },
        error: function (e) {
            console.log(e);
        },
        dataType: "json",
        contentType: "application/json",
    });
}
function changePollingDivision(districtId,dependId) {
    $.ajax({
        type: 'POST',
        url: '/get-divisions-by-district',
        data: JSON.stringify({"id": districtId}),
        success: function (data, status, xhr) {
            setDetails(data,dependId);
        },
        error: function (e) {
            console.log(e);
        },
        dataType: "json",
        contentType: "application/json",
    });
}

function setDetails(data,dependId){
    let l = " <option selected disabled>-- Select --</option>";
    for (let i = 0;i<data.length;i++){
        l+="<option value=\""+data[0]['id']+"\">"+data[0]['name']+"</option>";
    }
    document.getElementById(dependId).innerHTML = l;
}