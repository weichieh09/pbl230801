<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">獎勵</h3>
        <hr />
      </b-col>
      <b-col cols="12">
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
    <b-row v-if="this.rtss.length > 0">
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <th @click="changeOrder('plyr_nm')">
                <h5>姓名</h5>
              </th>
              <th :class="sort.type === 'plyr_lvl' ? 'table-success' : ''" @click="changeOrder('plyr_lvl')">
                <h5>等級</h5>
              </th>
              <th :class="sort.type === 'tot_wins' ? 'table-success' : ''" @click="changeOrder('tot_wins')">
                <h5>勝場</h5>
              </th>
              <th :class="sort.type === 'mtch_end_time' ? 'table-success' : ''" @click="changeOrder('mtch_end_time')">
                <h5>時間</h5>
              </th>
              <!-- <th style="width: 20%"><h5>註記</h5></th> -->
            </tr>
          </thead>
          <tbody>
            <tr v-for="(rts, index) in rtss" @click="checkFlag(rts)">
              <td :class="rts.chkFg === 'Y' ? 'table-warning' : ''">
                <h5><b-icon icon="GiftFill" style="color: red" v-if="rts.chkFg === 'Y'" /> {{ rts.plyrNm }}</h5>
              </td>
              <td :class="rts.chkFg === 'Y' ? 'table-warning' : ''">
                <h5>{{ rts.plyrLvl }}</h5>
              </td>
              <td :class="rts.chkFg === 'Y' ? 'table-warning' : ''">
                <h5>{{ rts.totWins }}</h5>
              </td>
              <td :class="rts.chkFg === 'Y' ? 'table-warning' : ''">
                <h5>{{ rts.mtchEndTime }}</h5>
              </td>
              <!-- <td>
                <h5>{{ rts.chkFg }}</h5>
              </td> -->
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-pagination v-model="page.currentPage" :total-rows="page.objTotal" :per-page="page.perPage" @input="pageLoad(page.currentPage)" />
      </b-col>
    </b-row>
    <b-row v-else-if="this.isNoData">
      <b-col cols="12">
        <b-alert show variant="warning">該賽事尚未有戰績登錄!</b-alert>
      </b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">請選擇<strong> 隊伍 </strong>及<strong> 賽事 </strong></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" @click="$router.go(-1)">返回</b-button>
      </b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script lang="ts" src="./wcc801.ts"></script>
