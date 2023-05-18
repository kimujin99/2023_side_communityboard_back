//csrf token
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
const csrfToken = document.querySelector('meta[name="_csrf"]').content;

//headers 에 csrfToken 설정
//json 타입
const jsonHeaders = {
    "Content-Type": "application/json",
};
jsonHeaders[csrfHeader] = csrfToken;

//multipart 타입
const multipartHeaders = {
};
multipartHeaders[csrfHeader] = csrfToken;