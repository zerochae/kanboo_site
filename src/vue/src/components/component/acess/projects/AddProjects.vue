<template>
    <div class="container">
        <ul class="item-list">
            <li @click="this.changeIsModalOpen" id="firstLi" class="list-item btn">
                    <div id="divdiv">
                        <i class="fas fa-plus-circle"></i>
                    </div>
            </li>
            <li class="list-item" v-for="item in this.$store.state.projectList.projectList" :key="item">
                <a @click="moveToDashBoard(item)">
                    <div class="detail">
                        <div class="prjct-name">{{ item.prjctNm }}</div>
                        <div class="detail-wrap">
                            <span>기간: {{ item.prjctStartDate }} ~ {{ item.prjctEndDate }}</span>
                        </div>
                        <div>팀장: {{item.prjctManager}}</div>
                    </div>
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
import {mapMutations} from 'vuex'


export default {
    name : 'AddProjects',
    data() {
        return {

        }
    },
    methods: {
        ...mapMutations({
            changeIsModalOpen : 'projectList/changeIsModalOpen',
            moveToDashBoard: 'global/moveToDashBoard'
        }),
    },
}
</script>

<style scoped>


.container {
    height: 100%;
    padding: 60px;
    width: 100%;
}

.item-list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-auto-rows: 100px;
    column-gap: 20px;
    row-gap: 30px;
    height: 100%;
    overflow: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}

.item-list::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
}

.item-list li {
    background-color: #2C2F3B;
    border-radius: 6px;
    padding: 10px;
    transition: ease-in-out 0.3s;
}
.detail {
    color : #717790;
}
.detail > div:nth-child(1) {
    border-bottom: 1px solid #717790 ;
    padding-bottom: 8px;
}
.detail > div:nth-child(2) {
    margin-top: 10px;
}

.detail > div:nth-child(3) {
    padding-top: 10px;
}

.btn {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 30px;
}

a {
    color: white;
}

#firstLi > a:nth-child(1) {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

li:hover {
    background-color: black;
}

li:hover .detail {
    color: white;
}

li:hover .prjct-name {
    border-bottom: 1px solid darkorchid;
}

/* .detail:hover {
    color: white;
} */

.detail-wrap {
  display: flex;
  justify-content: space-between;
}

</style>