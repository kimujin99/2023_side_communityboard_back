const replyDeleteBtnList = document.querySelectorAll('.delete-reply-btn');
const modal = document.querySelector('dialog');
const modalDeleteBtn = document.querySelector('#modal_del_btn');
const modalCloseBtn = document.querySelector('#close_delete_modal');
const postingDeleteBtn = document.querySelector('#delete-posting-btn');

const addDeleteBtnEvent = () => {

    replyDeleteBtnList.forEach((btn) => btn.addEventListener('click', (e) => {
    const id = e.currentTarget.dataset.reply;
    modal.showModal();
    modalDeleteBtn.addEventListener('click', () => deleteReply(id));

}))

if(postingDeleteBtn != null) {

    postingDeleteBtn.addEventListener('click', () => {

        const id = postingDeleteBtn.dataset.posting;
        modal.showModal();
        modalDeleteBtn.addEventListener('click', () => deletePosting(id));

    })
}


}

//fetch API
async function deleteContent(url, flag) {
    try {

        const response = await fetch(url, {
            method: 'DELETE',
            headers: jsonHeaders,
        });

        //성공 시 새로고침
        if(response.status === 200) {
            if(flag == 'P') {
                window.location.href='/boards';
            } else if(flag == 'R') {
                location.reload();
            }
        }

    } catch(err) {
        console.error(err);
    }
}

async function deletePosting(id) {
    const url = `/boards/${id}`
    await deleteContent(url, 'P')
}

async function deleteReply(id) {
    const url = `/boards/reply/${id}`
    await deleteContent(url, 'R')
}

modalCloseBtn.addEventListener('click', () => {
    modal.close();
})

// init
addDeleteBtnEvent();





