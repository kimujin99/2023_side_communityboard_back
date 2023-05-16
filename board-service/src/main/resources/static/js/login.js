const loginSubmitBtn = document.querySelector('#login-submit-btn');

const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');

const emailErr = document.querySelector('#emailErr');
const emailErrSpan = document.querySelector('#emailErr span');
const passwordErr = document.querySelector('#passwordErr');
const passwordErrSpan = document.querySelector('#passwordErr span');

//email 공백 금지
emailInput.addEventListener('keyup', () => {
    const email = emailInput.value;
    emailInput.value = email.trim();
})

//email 유효성 체크
function validateEmail() {
    const email = emailInput.value;

    if(email.trim() == '' || email == null) {
        emailErr.style.display = 'block';
    }
}

//password 공백 금지
passwordInput.addEventListener('keyup', () => {
    const password = passwordInput.value;
    passwordInput.value = password.trim();
})

//password 유효성 체크
function validatePassword() {
    const password = passwordInput.value;

    if(password.trim() == '' || password == null) {
        passwordErr.style.display = 'block';
    }
}

loginSubmitBtn.addEventListener('click', () => {
    emailErr.style.display = 'none';
    passwordErr.style.display = 'none';
    authErr.style.display = 'none';
})

emailInput.addEventListener('invalid', (e) => {
    // 브라우저 툴팁 숨김
    e.preventDefault();
    console.log("email eventListener - invalid 진입")

    validateEmail();
})


passwordInput.addEventListener('invalid', (e) => {
    // 브라우저 툴팁 숨김
    e.preventDefault();

    validatePassword();
})






