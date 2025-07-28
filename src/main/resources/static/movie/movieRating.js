// 백엔드 API 서버의 기본 URL
const URL = 'api/v1/practice/movie-ratings';

//=========== 렌더링 관련 함수 ============//

// 영화 리뷰를 화면에 랜더링 하는 함수
const renderReviews = function (reviewList) {

    // 1. 리뷰 목록을 담을 태그를 가져옵니다.
    const $reviewList = document.getElementById("reviewsList");
    const $noReviews = $reviewList.querySelector('.no-reviews');

    // 2. 렌더링을 시작하기 전에 기존 목록을 전부 비웁니다.
    // 이 과정이 없으면 목록을 새로고침할 때마다 책들이 중복되어 나타납니다.
    $reviewList.innerHTML = ` `;

    // 3. 받아온 리뷰 목록(reviewList)을 순회하면서 태그를 생성합니다.
    reviewList.forEach(review => {

        const $reviewElement = document.createElement('div');
        $reviewElement.className = 'review-item';

        // 생성된 div 태그에 data-id 속성으로 리뷰의 고유 ID를 부여합니다. (삭제 시 사용)
        $reviewElement.dataset.id = review.id;

        // 10점 만점을 별점으로 표시 (2점당 별 1개)
        const starCount = review.rating;
        const stars = '★'.repeat(starCount) + '☆'.repeat(5 - starCount);

        $reviewElement.innerHTML = `
            <div class="review-header">
                <div class="movie-title">
                    ${review.movie_title}
                    <button class="del-btn">🗑️ 삭제</button>
                    <button class="edit-btn">✏️ 수정</button>
                 </div>
                <div class="review-rating">${stars} (${review.rating}/5)</div>
            </div>
            <div class="review-content">${review.review}</div>
            <div class="review-author">${review.watched_at}</div>
        `;

        $reviewList.append($reviewElement)
    })
}

//=========== 서버 데이터 요청/응답 관련 함수 ============//

// 서버에서 리뷰 목록을 가져오는 비동기 함수
const fetchGetReviews = async () => {
    const res = await fetch(URL);
    const result = await res.json();
    console.log(result);
    // 받아온 데이터로 렌더링 함수를 호출합니다.
    renderReviews(result);
};

// 서버에 리뷰를 등록하는 비동기 함수
const fetchPostReview = async () => {

    // 1. 폼에서 사용자가 입력한 제목, 평점, 감상일, 리뷰 내용 정보를 가져옵니다.
    const $titleInput = document.getElementById('movieTitle');
    const $ratingInput = document.getElementById('rating');
    const $watchDateInput = document.getElementById('watchDate');
    const $reviewInput = document.getElementById('review');

    const payload = {
        movie_title: $titleInput.value,
        rating: $ratingInput.value,
        review: $reviewInput.value,
        watched_at: $watchDateInput.value
    };

    // 2. 입력값이 모두 채워졌는지 간단히 확인합니다.
    if (!$titleInput.value.trim() || !$ratingInput.value.trim() || !$watchDateInput.value || !$reviewInput.value.trim()) {
        alert('모든 필드를 채워주세요!');
        return;
    }

    // 유효성 검사
    if (!$ratingInput.value || $ratingInput.value < 1 || $ratingInput.value > 5) {
        alert('평점을 1 ~ 5 사이로 입력해주세요!');
        return;
    }

    // 3. 서버에 보낼 데이터를 객체로 만듭니다.
    /*const payload = {
        movie_title: $titleInput.value,
        rating: $ratingInput.value,
        review: $reviewInput.value,
        watched_at: $watchDateInput.value
    };*/

    // 4. fetch를 사용하여 POST 요청을 보냅니다.
    await fetch(`${URL}`, {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        body: JSON.stringify(payload)
    });

    // 5. 등록이 완료된 후, 입력창을 비우고 목록을 새로고침합니다.
    $titleInput.value = ``;
    $ratingInput.value= ``;
    $reviewInput.value= ``;
    $watchDateInput.value = ``;

    await fetchGetReviews();
}

// 리뷰를 삭제하는 비동기 함수
const fetchDeleteReview = async (id) => {

    // 정말 삭제할 것인지 사용자에게 한 번 더 확인합니다.
    if (!confirm(`${id}번 리뷰를 정말 삭제하시겠습니까?`)) {
        return;
    }

    // fetch를 사용하여 DELETE 요청을 보냅니다.
    const res = await fetch(`${URL}/${id}`, {
        method: 'DELETE',
    });

    await fetchGetReviews(); // 목록 새로고침

}

// 모달 오픈 함수
const openModal = () => {
    const $modalOverlay = document.querySelector('.modal-overlay');
    $modalOverlay.style.opacity = 1;
    $modalOverlay.style.visibility = 'visible';
}

// 모달 클로즈 함수
const closeModal = () => {
    const $modalOverlay = document.querySelector('.modal-overlay');
    $modalOverlay.style.opacity = 0;
    $modalOverlay.style.visibility = 'hidden';
}

//=========== 이벤트 핸들러 설정 ============//
const addEventListeners = () => {
    const reviewFrom = document.getElementById('reviewForm');
    const $reviewList = document.getElementById("reviewsList");
    const $modalCloseBtn = document.querySelector('.modal-close-btn');
    const $modalCancelBtn = document.querySelector('.btn-cancel');

    // 리뷰 등록 폼 제출 이벤트
    reviewFrom.addEventListener('submit', (e) => {
        // 폼 제출 시 브라우저가 새로고침되는 기본 동작을 막습니다.
        e.preventDefault();
        fetchPostReview();

        e.target.reset();
    });

    // 리뷰 삭제 이벤트
    $reviewList.addEventListener('click', e => {

        if(!e.target.matches('.del-btn'))  return;

        console.log("삭제 버튼 클릭!")
        // 클릭된 삭제 버튼에서 가장 가까운 li 태그를 찾아 data-id 값을 가져옵니다.
        const reviewId = e.target.closest('.review-item').dataset.id;
        fetchDeleteReview(reviewId);

    });

    // 리뷰 수정 이벤트
    $reviewList.addEventListener('click', e => {

        if(!e.target.matches('.edit-btn')) return;

        console.log("수정 버튼 클릭!");
        // 모달 오픈 이벤트
        openModal();

        const reviewId = e.target.closest('.review-item').dataset.id;
        // 개별 조회 > 모달에 데이터 랜더링 > 수정

    });

    // 모달 닫기 이벤트
    $modalCloseBtn.addEventListener('click', e => {
        closeModal();
    });
    $modalCancelBtn.addEventListener('click', e => {
        closeModal();
    })

}

//=========== 메인 코드 실행 ============//
(function () {
    // 페이지가 처음 열렸을 때 기본 목록(id순)을 불러옵니다.
    fetchGetReviews();

    // 이벤트 핸들러들을 등록합니다.
    addEventListeners();
})();
