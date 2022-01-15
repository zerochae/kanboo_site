<template>
  <div>
    <div class="how-to-use-container">
      <div class="how-title-box">How To Use</div>
      <div class="how-stack">
        <div
          class="how-cards"
          v-for="card in cards"
          :key="card"
          :id="`card${card.id}`"
        >
          <div class="how-card">
            <img
              class="how-card-img"
              :src="require(`@/assets/${card.img}`)"
              alt="img"
            />
            <div class="how-card-content">
              <div class="how-card-text">
                <div class="how-card-title">
                  {{ card.title }}
                </div>
                {{ card.content }}
              </div>
              <button @click="swap(card.id)" class="how-btn-next">
                <i class="fas fa-chevron-right"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  mounted() {},
  data() {
    return {
      cards: [
        {
          id: "7",
          title: "Voice Chat",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con3.jpg",
        },
        {
          id: "6",
          title: "VIEW",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con1.jpg",
        },
        {
          id: "5",
          title: "ERD",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con3.jpg",
        },
        {
          id: "4",
          title: "demand",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con1.jpg",
        },
        {
          id: "3",
          title: "kanban",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con3.jpg",
        },
        {
          id: "2",
          title: "gantt",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con1.jpg",
        },
        {
          id: "1",
          title: "compiler",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con3.jpg",
        },
        {
          id: "0",
          title: "create project",
          content: `This is the space where you can compile within the website. 
You can simply check the code tests and test results, share them with your team members, and discuss them. 
How many people can interpret this in English? There doesn't seem to be!`,
          img: "con1.jpg",
        },
      ],
    };
  },
  methods: {
    swap(i) {
      for (let j = 0; j < 8; j++) {
        let target = document.getElementById(`card${j}`);
        target.style.animation = "";
      }

      let card = this.cards.pop();
      let thisCard = document.getElementById(`card${i}`);
      thisCard.classList+=(' how-swap')
      console.log(thisCard)
      setTimeout( ()=>{
        thisCard.className = 'how-cards';
      },2000)
      this.cards.unshift(card);
    },
  },
};
</script>

<style scoped>
.how-to-use-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  color: #fff;
  --y: calc(100vh - 70px);
  height: var(--y);
  overflow: hidden;
  margin-top: 70px;
}

.how-to-use-container,
.how-to-use-container:before,
.how-to-use-container::after {
  box-sizing: border-box;
}

.how-stack {
  margin-top: 50px;
  max-width: 1200px;
  max-height: 600px;
  width: 80vw;
  height: 70vh;
  position: relative;
  
}

.how-title-box {
  margin-top: 20px;
  font-size: 44px;
  position: relative;
  z-index: 9999;
}

.how-cards {
  padding: 15px;
  background: rgb(44, 47, 59);
  background: linear-gradient(
    60deg,
    rgba(44, 47, 59, 1) 8%,
    rgba(69, 72, 87, 0.9640231092436975) 42%,
    rgba(116, 120, 138, 1) 81%
  );

  width: 80%;
  min-height: 40%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: grid;
  justify-content: center;
  align-items: center;
  border-radius: 2rem;
  box-shadow: 0 5px 25px 0 rgba(236, 237, 247, 0.2),
    0 15px 25px 0 rgba(12, 124, 209, 0.1);
  transition: transform 2000ms;
  z-index: 3;
  
}

.how-cards:nth-last-child(n + 6) {
  --y: calc(-50% - 170px);
  transform: translate(-50%, var(--y)) scale(0.8);
  opacity: 0;
}
.how-cards:nth-last-child(5) {
  --y: calc(-50% - 135px);
  transform: translate(-50%, var(--y)) scale(0.85);
  opacity: 0.1;
}
.how-cards:nth-last-child(4) {
  --y: calc(-50% - 90px);
  transform: translate(-50%, var(--y)) scale(0.9);
  opacity: 0.3;
}
.how-cards:nth-last-child(3) {
  --y: calc(-50% - 45px);
  transform: translate(-50%, var(--y)) scale(0.95);
  opacity: 0.4;
}
.how-cards:nth-last-child(2) {
  --y: calc(-50%);
  transform: translate(-50%, var(--y)) scale(1);
  opacity: 0.9;
}
.how-cards:nth-last-child(1) {
  --y: calc(-50% + 45px);
  transform: translate(-50%, var(--y)) scale(1.05);
  opacity: 1;
}

.how-card {
  padding: 10px;
  margin: 15px;
  position: relative;
}

.how-card-img {
  width: 320px;
  height: 440px;
  float: left;
  margin-right: 50px;
  border-radius: 25px;
}

.how-card-content {
  text-align: center;
  display: inline;
  margin: 15px 0px 0px 30px;
}

.how-card-text {
  display: flex;
  flex-direction: column;
  text-align: left;
  padding: 50px;
  font-size: 22px;
}

.how-card-title {
  margin-bottom: 30px;
  text-align: left;
  font-size: 32px;
}

.how-btn-next {
  position: absolute;
  left: 95%;
  bottom: 45%;
  font-size: 44px;
  color: white;
}

.how-swap{
  animation: swap 1200ms forwards;
}

@keyframes swap {
  0% {
    z-index: 50;
    opacity: 1;
  }
  50% {
    transform: translate(50%) scale(0.85) rotate(-15deg);
    animation-timing-function: ease-in;
    opacity: 0.4;
  }
  100% {
    transform: translate(-50% - 90px) scale(0.85);
    z-index: -1;
    opacity: 0.01;
  }
}
</style>