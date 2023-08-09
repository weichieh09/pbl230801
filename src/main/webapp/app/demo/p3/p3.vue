<template>
  <b-container fluid>
    <b-row>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg">總覽頁面</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg">賽事管理</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary" size="lg">球隊管理</b-button>
      </b-col>
    </b-row>
    <!-- <b-row>
      <b-col cols="12">
        <b-card title="球隊成績" class="mb-2">
          <b-card-text>
            <b-row>
              <b-col cols="12">
                <b-table striped hover :items="items" :fields="fields" />
              </b-col>
            </b-row>
          </b-card-text>
        </b-card>
      </b-col>
    </b-row> -->
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group prepend="球隊">
          <b-form-input value="運動家羽球隊" />
        </b-input-group>
        <br />
        <b-input-group prepend="日期">
          <b-form-input type="date" value="2020-01-01" />
        </b-input-group>
        <br />
        <b-input-group prepend="場地">
          <b-form-input value="台藝大" />
        </b-input-group>
        <br />
        <b-input-group prepend="賽事">
          <b-form-input value="爭分奪勝搶水果" />
        </b-input-group>
        <br />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="4">
        <b-button variant="outline-primary">勝場數</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary">時間序</b-button>
      </b-col>
      <b-col cols="4">
        <b-button variant="outline-primary">級數</b-button>
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
        <br />
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p3.ts"></script>
