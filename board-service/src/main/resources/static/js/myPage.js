const postingsTabBtn = document.querySelector('#postings-tab-btn');
const postingsTabAnchor = document.querySelector('#postings-tab-anchor');

const userInfoTabBtn = document.querySelector('#user-info-tab-btn');
const userInfoTabAnchor = document.querySelector('#user-info-tab-anchor');

const postingsTabContent = document.querySelector('#postings-tab-content');
const userInfoTabContent = document.querySelector('#user-info-tab-content');

const checkedTabImgUrl = '/img/ui/file_tab_checked_y.png';
const uncheckedTabImgUrl = '/img/ui/file_tab_unchecked_w.png';

const submitBtn = document.querySelector('#submit-update-btn');
const userCodeInput = document.querySelector('#user-code-input');

postingsTabAnchor.addEventListener('click', () => {
    postingsTabBtn.src = checkedTabImgUrl;
    userInfoTabBtn.src = uncheckedTabImgUrl;
    postingsTabContent.style.display = 'flex';
    userInfoTabContent.style.display = 'none';
})

userInfoTabAnchor.addEventListener('click', () => {
    userInfoTabBtn.src = checkedTabImgUrl;
    postingsTabBtn.src = uncheckedTabImgUrl;
    userInfoTabContent.style.display = 'flex';
    postingsTabContent.style.display = 'none';
})

submitBtn.addEventListener('click', () => {
    validateNickname();

    if(nicknameChecking && profileChecking) {
        if(profileInput.files.length != 0) {
            uploadProFileAndUpdateUser();
        } else if(profileInput.files.length == 0) {
            updateUser();
        }
    }
})

//fetch API
let uploadedProfileImgPath = null;

async function uploadProFileAndUpdateUser() {
    let formData = new FormData();
    formData.append('profile', profileInput.files[0]);

    const url ="/profile-upload.do";

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: multipartHeaders,
            body: formData
        });

        //성공 시 새로고침
        if(response.status === 200) {
            const data = await response.json(); // response.json()으로 Promise 객체 추출
            uploadedProfileImgPath = data.data; // data에서 결과 추출

            updateUser();
        }

    } catch(err) {
        console.error(err);
    }
}

//fetch API
async function updateUser() {
    const url = '/boards/my/edit';
    const data = {
        userCode: userCodeInput.value,
        userNickname: nicknameInput.value,
        userProfile: uploadedProfileImgPath,
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: jsonHeaders,
            body: JSON.stringify(data)
        });

        //성공 시 새로고침
        if(response.status === 200) {
            window.location.href='/boards/my';
        }

    } catch(err) {
        console.error(err);
    }
}