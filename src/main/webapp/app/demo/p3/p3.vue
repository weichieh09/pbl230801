<template>
  <b-container fluid>
    <!-- <b-row>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg">總覽頁面</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg" to="/event-z">賽事管理</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg" to="/team">球隊管理</b-button>
      </b-col>
    </b-row> -->
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-select v-model="form.space" :options="spaces" v-on:change="spaceChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-input v-model="form.event" disabled />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="4">
        <b-button size="lg" variant="outline-primary">勝場數</b-button>
      </b-col>
      <b-col cols="4">
        <b-button size="lg" variant="outline-primary">時間序</b-button>
      </b-col>
      <b-col cols="4">
        <b-button size="lg" variant="outline-primary">級數</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <br />
        <table class="table">
          <thead>
            <tr>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
              <th scope="col">勝場數</th>
              <th scope="col">達成時間</th>
              <th scope="col">確認</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>12</td>
              <td>Mark</td>
              <td>3</td>
              <td>11:30</td>
              <td><b-button variant="outline-secondary" size="small">-</b-button></td>
            </tr>
            <tr>
              <td>14</td>
              <td>Jacob</td>
              <td>2</td>
              <td>11:40</td>
              <td><b-button variant="outline-secondary" size="small">-</b-button></td>
            </tr>
            <tr>
              <td>15</td>
              <td>Larry</td>
              <td>4</td>
              <td>11:45</td>
              <td><b-button variant="outline-secondary" size="small">-</b-button></td>
            </tr>
            <tr>
              <td>16</td>
              <td>Sam</td>
              <td>2</td>
              <td>11:55</td>
              <td><b-button variant="outline-secondary" size="small">-</b-button></td>
            </tr>
            <tr>
              <td>14</td>
              <td>Jack</td>
              <td>2</td>
              <td>11:40</td>
              <td><b-button variant="outline-secondary" size="small">-</b-button></td>
            </tr>
          </tbody>
        </table>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="my-table" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p3.ts"></script>
