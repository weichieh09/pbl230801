<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">戰績登錄</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="form.teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" disabled />
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
    <b-row v-if="this.form.event != null">
      <b-col cols="6">
        <h3 class="text-success" style="text-align: center">勝方</h3>
        <b-form-input type="number" size="lg" class="form-control-success" v-model="resultForm.wScr" placeholder="分數" /><br />
        <b-button block variant="outline-success" size="lg" @click="showModal('wPlyr1')">
          {{ resultForm.wPlyr1Nm }}
        </b-button>
        <br />
        <b-button block variant="outline-success" size="lg" @click="showModal('wPlyr2')">
          {{ resultForm.wPlyr2Nm }}
        </b-button>
      </b-col>
      <b-col cols="6">
        <h3 class="text-danger" style="text-align: center">敗方</h3>
        <b-form-input type="number" size="lg" class="form-control-danger" v-model="resultForm.lScr" placeholder="分數" /><br />
        <b-button block variant="outline-danger" size="lg" @click="showModal('lPlyr1')">
          {{ resultForm.lPlyr1Nm }}
        </b-button>
        <br />
        <b-button block variant="outline-danger" size="lg" @click="showModal('lPlyr2')">
          {{ resultForm.lPlyr2Nm }}
        </b-button>
      </b-col>

      <b-col cols="12">
        <br />
        <b-button block variant="outline-primary" size="lg" @click="checkPoint()">戰績登錄 <b-icon icon="cloudArrowUpFill" /></b-button>
      </b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">請選擇賽事</b-alert>
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

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal ref="selectPlyr-modal" hide-footer title="選擇選手">
          <!-- <b-row>
            <b-col cols="9">
              <b-form-input v-model="searchName" placeholder="姓名" size="lg" />
            </b-col>
            <b-col cols="3">
              <b-button block variant="outline-primary" size="lg">
                <b-icon icon="search" />
              </b-button>
            </b-col>
          </b-row> -->
          <b-row v-if="this.plyrs.length > 0">
            <b-col cols="12">
              <b-list-group v-for="item in plyrs" :key="item.id">
                <b-list-group-item button @click="hideModal(item)">
                  <b-row>
                    <b-col cols="12">
                      <h5 style="text-align: center">{{ item.plyrNm }}</h5>
                      <p style="text-align: center">{{ item.plyrLvl }}級</p>
                    </b-col>
                  </b-row>
                </b-list-group-item>
              </b-list-group>
              <br />
            </b-col>
            <b-col cols="12" class="d-flex justify-content-center">
              <b-pagination
                v-model="page.currentPage"
                :total-rows="page.objTotal"
                :per-page="page.perPage"
                @input="pageLoad(page.currentPage)"
              />
            </b-col>
          </b-row>
          <b-row v-else>
            <b-col cols="12">
              <b-alert show variant="warning">這支隊伍沒有人唷!</b-alert>
            </b-col>
            <b-col cols="12">
              <b-button block variant="outline-primary" @click="hideModal(null)">確定</b-button>
            </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal ref="checkPoint-modal" ok-title="確認" @ok="saveResultForm()" cancel-title="取消" title="請再次確認">
          <b-row>
            <!-- <b-col cols="12">
              <b-alert show variant="warning">66666!</b-alert>
            </b-col> -->
            <b-col cols="12"> 確認賽事結果無誤? </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>

    <br />
  </b-container>
</template>

<style>
.form-control-success {
  color: #28a745;
  border-color: #28a745;
  text-align: center;
}

.form-control-success:focus {
  color: #28a745;
  background-color: #fff;
  border-color: #28a745;
  outline: 0;
  -webkit-box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.5);
  box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.5);
}

.form-control-success::placeholder {
  color: #28a745;
  text-align: center;
}

.form-control-danger {
  color: #dc3545;
  border-color: #dc3545;
  text-align: center;
}

.form-control-danger:focus {
  color: #dc3545;
  background-color: #fff;
  border-color: #dc3545;
  outline: 0;
  -webkit-box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
  box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
}

.form-control-danger::placeholder {
  color: #dc3545;
  text-align: center;
}
</style>

<script lang="ts" src="./p2.ts"></script>
