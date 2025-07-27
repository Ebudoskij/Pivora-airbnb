document.addEventListener('DOMContentLoaded', () => {
    const sliders = document.querySelectorAll('.slider');

    sliders.forEach((slider, idx) => {
        const slides = slider.querySelectorAll(`.property-image-${idx}`);
        const nextBtn = slider.querySelector(`.next-${idx}`);
        const prevBtn = slider.querySelector(`.prev-${idx}`);
        let slideIndex = 0;
        let intervalId = null;

        function showSlide(index) {
            if (index >= slides.length) {
                slideIndex = 0;
            } else if (index < 0) {
                slideIndex = slides.length - 1;
            } else {
                slideIndex = index;
            }

            slides.forEach(slide => slide.classList.remove('displaySlide'));
            if (slides[slideIndex]) {
                slides[slideIndex].classList.add('displaySlide');
            }
        }

        function nextSlide() {
            clearInterval(intervalId);
            slideIndex++;
            showSlide(slideIndex);
            intervalId = setInterval(nextSlide, 10000);
        }

        function prevSlide() {
            clearInterval(intervalId);
            slideIndex--;
            showSlide(slideIndex);
            intervalId = setInterval(nextSlide, 10000);
        }

        if (slides.length > 0) {
            showSlide(slideIndex);
            intervalId = setInterval(nextSlide, 10000);
        }

        if (nextBtn) nextBtn.addEventListener('click', nextSlide);
        if (prevBtn) prevBtn.addEventListener('click', prevSlide);
    });
});