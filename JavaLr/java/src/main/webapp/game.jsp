<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Змейка</title>
    <style>
        canvas { border: 1px solid #000; }
        canvas:focus {
            box-shadow: 0 0 3px 1px blue; /* Визуальный индикатор фокуса */
        }
    </style>
</head>
<body>
    <canvas id="gameCanvas" width="600" height="600" tabindex="0"></canvas>
    <div>Счёт: <span id="score">0</span></div>

    <script>
        const canvas = document.getElementById('gameCanvas');
        const ctx = canvas.getContext('2d');
        let currentDirection = 'RIGHT';
        document.addEventListener('keydown', (e) => {
            if (e.target !== canvas) {
                e.preventDefault();
                canvas.focus();
            }
        });

        function drawGame(game) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            
            // Отрисовка змейки
            ctx.fillStyle = '#2ecc71';
            game.snake.forEach((segment, index) => {
                ctx.fillRect(
                    segment.x * 20, 
                    segment.y * 20, 
                    18, 18
                );
            });

            // Отрисовка цели
            ctx.fillStyle = '#e74c3c';
            ctx.fillRect(
                game.target.x * 20, 
                game.target.y * 20, 
                18, 18
            );

            document.getElementById('score').textContent = game.score;
        }

        let isProcessing = false; // Флаг для защиты от спама клавишами

        // Фокусировка canvas
        const initFocus = () => {
            canvas.tabIndex = 0;
            canvas.focus();
            canvas.style.outline = 'none'; // Убираем стандартную подсветку фокуса
        };
        initFocus();

        // Обработчик клавиш
        const handleKeyDown = (e) => {
            if (isProcessing) return;
            
            e.preventDefault();
            const newDirection = getDirectionFromKey(e.key);
            
            if (newDirection && newDirection !== currentDirection) {
                currentDirection = newDirection;
                isProcessing = true;
                setTimeout(() => isProcessing = false, 100); // Задержка между нажатиями
            }
        };

        // Сопоставление клавиш с направлениями
        const getDirectionFromKey = (key) => {
            const keyDirectionMap = {
                'ArrowUp': 'UP',
                'ArrowDown': 'DOWN',
                'ArrowLeft': 'LEFT',
                'ArrowRight': 'RIGHT'
            };
            return keyDirectionMap[key] || null;
        };

        // Вешаем обработчик на canvas
        document.addEventListener('keydown', (e) => {
            e.preventDefault();  // чтобы не происходил скроллинг и другие действия
            const newDirection = getDirectionFromKey(e.key);
            if (newDirection && newDirection !== currentDirection) {
                currentDirection = newDirection;
                console.log("New direction set to:", currentDirection);
            }
        });

        // Автофокус при любом клике на странице
        document.addEventListener('click', initFocus);

        // Модифицированная функция update()
        function update() {
            console.log('Sending direction:', currentDirection);
            const params = new URLSearchParams();
            params.append("direction", currentDirection);
            
            fetch('/update', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                body: params.toString()
            })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error ${response.status}`);
                return response.json();
            })
            .then(game => {
                drawGame(game);
                if (!game.gameOver) setTimeout(update, 200);
            })
            .catch(error => console.error('Fetch error:', error));
        }

        // Запуск игры
        update();
    </script>
</body>
</html>