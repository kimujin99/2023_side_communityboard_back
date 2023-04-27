const modal = document.querySelector("dialog")

function showModalAndSetDelLink(flag, postingCode, replyCode) {
    const modal_del_btn_P = document.getElementById("modal_del_btn_P")
    const modal_del_btn_R = document.getElementById("modal_del_btn_R")

    if(flag === "posting") {
        modal_del_btn_P.style.display = "block";
        modal_del_btn_R.style.display = "none";
    } else if (flag === "reply") {
        modal_del_btn_P.style.display = "none";
        modal_del_btn_R.style.display = "block";
        modal_del_btn_R.setAttribute("onclick", "location.href='/boards/"+postingCode+"/"+replyCode+"/delete'");
    }

    modal.showModal();
}

//삭제 모달에서 취소 버튼 누르면 모달 닫힘
document.getElementById("close_delete_modal").addEventListener("click", ()=>{
    modal.close();
});