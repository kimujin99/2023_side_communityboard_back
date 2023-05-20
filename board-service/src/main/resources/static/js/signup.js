//modal
const modal = document.querySelector('dialog');
const emailDuplicateCheckBtn = document.querySelector('#email-duplicate-check-btn');
const closeModalBtn = document.querySelector('#close_email_checking_modal');
const modalMsg = document.querySelector('#email-check-modal-msg');

//input
const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');

//error
const passwordErr = document.querySelector('#passwordErr');
const passwordErrSpan = document.querySelector('#passwordErr span');
const emailErr = document.querySelector('#emailErr');
const emailErrSpan = document.querySelector('#emailErr span');

//checked
const passwordChecked = document.querySelector('#passwordChecked');
const emailChecked = document.querySelector('#emailChecked');

const submitSignupBtn = document.querySelector('#submit-signup-btn');

//체킹 변수
let emailValidChecking = false;
let emailDuplicateChecking = false;

let passwordChecking = false;

//email 공백 금지
emailInput.addEventListener('keyup', () => {
    const email = emailInput.value;
    emailInput.value = email.trim();
})

//email 변경 감지
emailInput.addEventListener('change', () => {
    emailValidChecking = false;
    emailDuplicateChecking = false;
    emailChecked.style.display = 'none';
})

//email 유효성 체크
function validateEmail() {
    const email = emailInput.value;
    const emailRegex = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

    emailChecked.style.display = 'none';

    if(email.trim() == '' || email == null) {
        emailValidChecking = false;
        emailErrSpan.innerText = 'ERROR : 이메일을 입력해주세요!';
        emailErr.style.display = 'block';
    } else if(!emailRegex.test(email)) {
        emailValidChecking = false;
        emailErrSpan.innerText = 'ERROR : 이메일 형식에 맞게 입력해주세요!';
        emailErr.style.display = 'block';
    } else {
        emailErr.style.display = 'none';
        emailValidChecking = true;
    }
}

//email 중복체크
emailDuplicateCheckBtn.addEventListener('click', ()=>{
    validateEmail();
    if(emailValidChecking) {
        //fetch
        emailDuplicateCheck();
    }
})

//Modal 닫기
closeModalBtn.addEventListener('click', ()=>{
    modal.close();
})

//password 공백 금지
passwordInput.addEventListener('keyup', () => {
    const password = passwordInput.value;
    passwordInput.value = password.trim();
})

//password 변경 감지
passwordInput.addEventListener('change', () => {
    passwordChecking = false;
    passwordChecked.style.display = 'none';
})

//password 유효성 체크
function validatePassword() {
    const password = passwordInput.value;
    const passwordRegex = new RegExp('^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$');

    if(password.trim() == '' || password == null) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호를 입력해주세요!';
        passwordErr.style.display = 'block';
    } else if(password.length < 6 || password.length > 16) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호는 6~16자 사이입니다!';
        passwordErr.style.display = 'block';
    } else if(!passwordRegex.test(password)) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호는 한 개 이상의 특수문자와 숫자를 포함합니다!';
        passwordErr.style.display = 'block';
    } else {
        passwordErr.style.display = 'none';
        passwordChecked.style.display = 'block';
        passwordChecking = true;
    }
}

submitSignupBtn.addEventListener('click', () =>{

    if(!emailDuplicateChecking) {
        modalMsg.innerText = '이메일 중복체크를 진행해주세요!'
        modal.showModal();
    } else {

        validatePassword();
        validateNickname();

        //fetch
        if(passwordChecking && nicknameChecking && profileChecking) {
            if(profileInput.files.length != 0) {
                uploadProFileAndSignup();
            } else if (profileInput.files.length == 0) {
                signup();
            }
        }

    }

})

//fetch API
let uploadedProfileImgPath = null;

async function uploadProFileAndSignup() {
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

            signup();
        }

    } catch(err) {
        console.error(err);
    }
}

//fetch API
async function signup() {
    const url = '/signup';
    const data = {
        userEmailId: emailInput.value,
        userPassword: passwordInput.value,
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
            window.location.href='/login';
        }

    } catch(err) {
        console.error(err);
    }
}

//fetch API
async function emailDuplicateCheck() {
    const url = '/email-check.do';
    const data = {
        userEmailId: emailInput.value
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: jsonHeaders,
            body: JSON.stringify(data)
        });

        //성공 시 새로고침
        if(response.status === 200) {
            const data = await response.json(); // response.json()으로 Promise 객체 추출
            emailDuplicateChecking = data.data; // data에서 boolean 결과 추출

            console.log("emailDuplicateChecking = " + emailDuplicateChecking);

            //결과 분기
            if(emailDuplicateChecking) {
                emailChecked.style.display = 'block';
            } else {
                modalMsg.innerText = '이미 사용중인 이메일입니다!'
                modal.showModal();
            }
        }

    } catch(err) {
        console.error(err);
    }
}
