<template>
  <div>
    <div class="home-video-box">
      <div class="home-bg-video">
        <video src="@/assets/videoBanner.webm" autoplay muted></video>
        <router-link class="start" to="/demo">Start Now</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.home-video-box video {
  display: block;
  width: 1900px;
  height: calc(100vh - 70px);
  margin: auto;
  -webkit-user-drag: none;
  -khtml-user-drag: none;
  -moz-user-drag: none;
  -o-user-drag: none;
}

.home-bg-video{
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.home-bg-video .start {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  color: white;
  width: 240px;
  height: 60px;
  background: #FF8906;
  border-radius: 15px;
  font-size: 33px;
}
</style>