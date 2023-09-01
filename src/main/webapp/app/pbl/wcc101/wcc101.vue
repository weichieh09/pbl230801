<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">戰績排行榜</h3>
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
        <table class="table table-hover">
          <thead>
            <tr>
              <th>
                <h5>等級</h5>
              </th>
              <th>
                <h5>姓名</h5>
              </th>
              <th>
                <h5>勝場</h5>
              </th>
              <th>
                <h5>時間</h5>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(rts, index) in rtss">
              <td>
                <h5>{{ rts.plyrLvl }}</h5>
              </td>
              <td>
                <h5>{{ rts.plyrNm }} <b-icon icon="TrophyFill" style="color: orange" animation="fade" v-if="getIcon(index)" /></h5>
              </td>
              <td>
                <h5>{{ rts.totWins }}</h5>
              </td>
              <td>
                <h5>{{ rts.mtchEndTime }}</h5>
              </td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12">
        <b-pagination
          v-model="page.currentPage"
          :total-rows="page.objTotal"
          :per-page="page.perPage"
          @input="pageLoad(page.currentPage)"
          align="fill"
        />
      </b-col>
    </b-row>
    <b-row v-else-if="this.isNoData">
      <b-col cols="12">
        <b-alert show variant="warning">該賽事尚未有資料登錄!</b-alert>
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
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" @click="test()">管理功能</b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/pbl/wcc201">戰績登錄</b-button>
      </b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script lang="ts" src="./wcc101.ts"></script>
