//csrf token
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
const csrfToken = document.querySelector('meta[name="_csrf"]').content;

//headers 에 csrfToken 설정
//json 타입
const jsonHeaders = {
    "Content-Type": "application/json",
};
jsonHeaders[csrfHeader] = csrfToken;

//multipart form 타입
const MultipartHeaders = {
    "Content-Type": "multipart/form-data",
};
formHeaders[csrfHeader] = csrfToken;