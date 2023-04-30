const replyDeleteBtnList = document.querySelectorAll('.delete-reply-btn');
const modal = document.querySelector('dialog');
const modalDeleteBtn = document.querySelector('#modal_del_btn');
const modalCloseBtn = document.querySelector('#close_delete_modal');

const addDeleteBtnEvent = () => {

    replyDeleteBtnList.forEach((btn) => btn.addEventListener('click', (e) => {
    const id = e.currentTarget.dataset.reply;
    modal.showModal();
    modalDeleteBtn.addEventListener('click', () => deleteReply(id));

}))

}

async function deleteContent(url) {
    try {

        const {ok, ...response} = await fetch(url, {
            method: 'DELETE'
        });

        //성공 시 새로고침
        if(ok) {
            location.reload(true);
        }

    } catch(err) {
        console.error(err);
    }
}

async function deletePosting(id) {
    const url = `/boards/${id}`
    await deleteContent(url)
}

async function deleteReply(id) {
    const url = `/boards/reply/${id}`
    await deleteContent(url)
}

modalCloseBtn.addEventListener('click', () => {
    modal.close();
})

// init
addDeleteBtnEvent();





