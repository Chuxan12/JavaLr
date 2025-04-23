<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/_layout.jspf" %>

<div class="main">
  <div class="card" style="margin-bottom:20px">
     Length: <span id="len">1</span> &nbsp;|&nbsp;
     Time: <span id="time">0</span>s &nbsp;|&nbsp;
     Score: <span id="score">0</span>
  </div>

  <canvas id="gameCanvas" width="600" height="600" tabindex="0"></canvas>
</div>

<style>
  canvas { border:1px solid #000; }
</style>

<script>
    const canvas = document.getElementById('gameCanvas');
    const ctx    = canvas.getContext('2d');

    // HUD elements
    const lenEl   = document.getElementById('len');
    const timeEl  = document.getElementById('time');
    const scoreEl = document.getElementById('score');

    let currentDirection = 'RIGHT';
    let isProcessing = false; // key‑spam guard

    // helper: focus canvas
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
            setTimeout(() => isProcessing = false, 100); // debounce
        }
        e.preventDefault();
    });

    // draw entire frame
    function drawGame(game) {
        ctx.clearRect(0,0,canvas.width,canvas.height);

        // snake body
        ctx.fillStyle = '#2ecc71';
        game.snake.forEach(seg => ctx.fillRect(seg.x*20, seg.y*20, 18, 18));

        // target apple
        ctx.fillStyle = '#e74c3c';
        ctx.fillRect(game.target.x*20, game.target.y*20, 18, 18);

        // update HUD
        scoreEl.textContent = game.score;
        lenEl.textContent   = game.length;
        timeEl.textContent  = game.elapsedSeconds;
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
                alert('Game over! Score: '+game.score);
            }
        })
        .catch(err => console.error('Fetch error:', err));
    }

    update();
</script>