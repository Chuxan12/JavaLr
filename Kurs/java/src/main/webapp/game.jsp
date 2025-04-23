<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layout.jspf" %>

<div class="main">
  <div class="card" style="margin-bottom:20px">
     Длина: <span id="len">1</span> &nbsp;|&nbsp;
     Время: <span id="time">0</span>s &nbsp;|&nbsp;
     Очки: <span id="score">0</span>
  </div>

  <canvas id="gameCanvas" width="600" height="600" tabindex="0"></canvas>
  <div id="gameOverDialog" class="dialog hidden">
    <h3>Игра окончена!</h3>
    <p>Ваши очки: <span id="finalScore"></span></p>

    <a class="btn" href="${pageContext.request.contextPath}/init">Сыграть еще</a>
    <a class="btn" href="${pageContext.request.contextPath}/main">Главное меню</a>
    </div>
</div>

<style>
  canvas { border:1px solid #000; }
</style>

<script>
    (() => {
        const canvas = document.getElementById('gameCanvas');
        const ctx    = canvas.getContext('2d');
    
        // HUD elements
        const lenEl   = document.getElementById('len');
        const timeEl  = document.getElementById('time');
        const scoreEl = document.getElementById('score');
    
        let currentDirection = 'RIGHT';
        let isProcessing = false; // key‑spam guard
    
        /* focus canvas so that it immediately receives key events */
        function focusCanvas(){ canvas.focus(); }
        focusCanvas();
        document.addEventListener('click', focusCanvas);
    
        // key → direction map
        const keyDirectionMap = {
            'ArrowUp':    'UP',
            'ArrowDown':  'DOWN',
            'ArrowLeft':  'LEFT',
            'ArrowRight': 'RIGHT'
        };
    
        canvas.addEventListener('keydown', e => {
            if (isProcessing) return;
    
            const newDir = keyDirectionMap[e.key];
            if (newDir && newDir !== currentDirection) {
                currentDirection = newDir;
                isProcessing = true;
                setTimeout(() => isProcessing = false, 100); // debounce 100 ms
            }
            e.preventDefault();
        });

        /* Рисуем сетку 30×30 (размер клетки 20px) */
        function drawGrid(){
            ctx.save();
            ctx.strokeStyle = '#e5e7eb'; // светло‑серый
            ctx.lineWidth   = 1;
            for(let x = 20; x < canvas.width; x += 20){
                ctx.beginPath();
                ctx.moveTo(x,0);
                ctx.lineTo(x,canvas.height);
                ctx.stroke();
            }
            for(let y = 20; y < canvas.height; y += 20){
                ctx.beginPath();
                ctx.moveTo(0,y);
                ctx.lineTo(canvas.width,y);
                ctx.stroke();
            }
            ctx.restore();
        }
    
        // draw entire frame
        function drawGame(game) {
            ctx.clearRect(0,0,canvas.width,canvas.height);
            drawGrid();
            /* snake body */
            ctx.fillStyle = '#2ecc71';
            game.snake.forEach(seg => ctx.fillRect(seg.x*20, seg.y*20, 18, 18));
    
            /* target apple */
            ctx.fillStyle = '#e74c3c';
            ctx.fillRect(game.target.x*20, game.target.y*20, 18, 18);
    
            /* HUD */
            scoreEl.textContent = game.score;
            lenEl.textContent   = game.length;
            timeEl.textContent  = game.elapsedSeconds;
        }
    
        // reveal final‑score dialog
        function showGameOver(score){
            document.getElementById('finalScore').textContent = score;
            document.getElementById('gameOverDialog').classList.remove('hidden');
            canvas.blur(); // блокируем дальнейший ввод
        }
    
        // main loop
        function update(){
            fetch('${pageContext.request.contextPath}/update', {
                method : 'POST',
                headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
                body   : 'direction='+encodeURIComponent(currentDirection)
            })
            .then(resp => { if(!resp.ok) throw new Error(resp.status); return resp.json(); })
            .then(game => {
                drawGame(game);
                if (!game.gameOver){
                    setTimeout(update, 200);
                } else {
                    showGameOver(game.score);
                }
            })
            .catch(err => console.error('Fetch error:', err));
        }
    
        update();
    })();
    </script>