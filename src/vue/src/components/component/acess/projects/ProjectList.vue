<template>
<div class="list-container">
    <div class="project-state">
        <div id="project-top">
            <div id="img-div">
                <img id="profile-img" src="@/assets/profile.png" @click="search">
            </div>
            <div id="nick-div">
                KADE
            </div>
        </div>
        <div id="profile-list">
            <div>
                <div>
                    Proceddings()
                </div>
                <div class="projects-div-ul">
                    <ul class="projects-ul">
                        <li v-for="i in 10" :key="i">
                            <span>
                                <i class="far fa-circle"></i>
                            </span>
                            <span class="test-span">
                                <router-link to="/community/free">test1</router-link>
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
                <div class="project-completed">
                    Completed()
                </div>
                <div class="projects-div-ul">
                    <div>
                        <ul class="projects-ul">
                            <li v-for="i in 10" :key="i">
                                <span>
                                    <i class="far fa-circle"></i>
                                </span>
                                <span class="test-span">
                                    <router-link to="/community/free">test1</router-link>
                                </span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="list-right-container">
        <div id="addproject-div">
            <AddProjects/>
        </div>
        <div id="changes-div">
            <Changes/>
        </div>
    </div>
</div>
</template>

<script>
import AddProjects from '@/components/component/acess/projects/AddProjects.vue'
import Changes from '@/components/component/acess/projects/LatestChanges.vue'

export default {
    name : 'ProjectList',
    data() {
        return {
            projectList : [],
            projectList : [],
        }
    },
    methods: {
        getProjectsList(){
            this.axios.get('/projectDetail.json').then(e => {
                this.projectList = e.data
            })

        }
    },
    components : {
        AddProjects,
        Changes,
    },
    mounted() {

    },
}
</script>

<style scoped>
.list-container {
    color : white;
    display: flex;
    height: calc(100vh - 70px);
}

.list-top-container {
    /* justify-content: space-around; */
    margin-top: 20px;
}

#profile-img {
    width : 50px;
    height: 50px
}
#nick-div {
    margin-left: 16px;
    height: 50px;
    width: fit-content;
    font-size: 30px;
    padding-top: 10px;
}

.project-state {
    width: 10vw;
    padding-top: 20px;
    padding-left: 30px;
    display: flex;
    flex-direction: column;
}

#profile-list {
    display: flex;
    flex-direction: column;
    margin-top: 30px;
}

#project-top {
    display: flex;
}

.projects-div-ul {
    margin-left: 20px;
    margin-top : 8px;
    height: 140px;
    overflow: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}

.projects-div-ul::-webkit-scrollbar {
    display: none;
}

#completed-div {
    margin-top: 10px;
}

.projects-ul a {
    color: #fff;
}
.test-span {
    margin-left: 10px;
}

.projects-ul li {
    margin-top: 10px;
}

.project-completed {
    margin-top: 10px;
}

.list-right-container {
    display: flex;
    flex-direction: column;
    margin-left: 20px;
    width: 80vw;
    height: 100%;
}

#addproject-div {
    /* height: 100%; */
    /* padding-top: 30px;
    padding-bottom: 30px; */
    height: 400px;
}

#changes-div {
    /* border: 1px solid red; */
    margin-top: 100px;
    height: 100%;
    font-size: 30px;
    width: 100%;
    padding-left: 60px;
}
</style>