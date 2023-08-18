<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">名單管理</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <h5 style="text-align: right; color: green">*參賽人數: {{ countJoined }}</h5>
      </b-col>
    </b-row>
    <br />
    <b-row v-if="plyrs.length > 0">
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <th style="width: 30%">
                <h5>等級</h5>
              </th>
              <th style="width: 40%">
                <h5>姓名</h5>
              </th>
              <th style="width: 30%">
                <h5>參賽</h5>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in plyrs" :key="index" @click="joinEvent(item)">
              <td :class="item.joinEv === 'Y' ? 'table-success' : ''">
                <h5>{{ item.plyrLvl }}</h5>
              </td>
              <td :class="item.joinEv === 'Y' ? 'table-success' : ''">
                <h5>{{ item.plyrNm }}</h5>
              </td>
              <td :class="item.joinEv === 'Y' ? 'table-success' : ''">
                <h5 v-if="item.joinEv === 'Y'"><b-icon font-scale="1.5" icon="CheckSquare" style="color: green" /></h5>
                <h5 v-else><b-icon font-scale="1.5" icon="DashSquare" style="color: gray" /></h5>
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
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">這支隊伍還沒有球員唷!</b-alert>
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

<script lang="ts" src="./wcc901.ts"></script>
