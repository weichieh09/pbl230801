<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="form.teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-select v-model="form.space" :options="form.spaces" v-on:change="spaceChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-select v-model="form.event" :options="form.events" v-on:change="eventChange()" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6" class="d-flex justify-content-center">
        <!-- <b-button variant="outline-primary" size="lg" @click="openLogin()">管理者登入</b-button> -->
        <b-button variant="outline-primary" size="lg" to="/demo/p3">管理者登入</b-button>
      </b-col>
      <b-col cols="6" class="d-flex justify-content-center">
        <b-button variant="outline-primary" size="lg" to="/demo/p2">球友登入</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row v-if="this.rtss.length > 0">
      <b-col cols="12" class="d-flex justify-content-center">
        <h3>即時戰績排行榜</h3>
      </b-col>
      <b-col cols="12">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>等級</th>
              <th>姓名</th>
              <th>勝場</th>
              <th>達成時間</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(rts, index) in rtss">
              <td>{{ rts.plyrLvl }}</td>
              <td>{{ rts.plyrNm }}</td>
              <td>{{ rts.totWins }}</td>
              <td>{{ rts.mtchEndTime }}</td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-pagination v-model="page.currentPage" :total-rows="page.objTotal" :per-page="page.perPage" @input="pageLoad(page.currentPage)" />
      </b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">尚未有資料</b-alert>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p1.ts"></script>
