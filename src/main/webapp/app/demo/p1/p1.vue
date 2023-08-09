<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group size="lg" prepend="球隊">
          <b-form-input value="運動家羽球隊" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" value="2020-01-01" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-input value="台藝大" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-input value="爭分奪勝搶水果" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6" class="d-flex justify-content-center">
        <b-button variant="outline-primary" size="lg" @click="openLogin()">管理者登入</b-button>
      </b-col>
      <b-col cols="6" class="d-flex justify-content-center">
        <b-button variant="outline-primary" size="lg" to="/demo/p2">球友登入</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <h5>即時戰績排行榜</h5>
      </b-col>
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">級數</th>
              <th scope="col">選手</th>
              <th scope="col">勝場數</th>
              <th scope="col">達成時間</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th scope="row">12</th>
              <td>Mark</td>
              <td>3</td>
              <td>11:30</td>
            </tr>
            <tr>
              <th scope="row">14</th>
              <td>Jacob</td>
              <td>2</td>
              <td>11:40</td>
            </tr>
            <tr>
              <th scope="row">15</th>
              <td>Larry</td>
              <td>4</td>
              <td>11:45</td>
            </tr>
            <tr>
              <th scope="row">16</th>
              <td>Sam</td>
              <td>2</td>
              <td>11:55</td>
            </tr>
            <tr>
              <th scope="row">14</th>
              <td>Jack</td>
              <td>2</td>
              <td>11:40</td>
            </tr>
            <tr>
              <th scope="row">15</th>
              <td>Timmy</td>
              <td>4</td>
              <td>11:45</td>
            </tr>
            <tr>
              <th scope="row">11</th>
              <td>Andy</td>
              <td>2</td>
              <td>11:55</td>
            </tr>
          </tbody>
        </table>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p1.ts"></script>
