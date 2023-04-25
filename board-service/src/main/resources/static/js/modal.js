const modal = document.querySelector("dialog")

document.getElementById("show_delete_modal").addEventListener("click", ()=>{
    modal.showModal();
});

document.getElementById("close_delete_modal").addEventListener("click", ()=>{
    modal.close();
});