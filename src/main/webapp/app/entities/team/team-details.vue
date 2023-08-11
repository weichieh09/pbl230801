<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="team">
        <h2 class="jh-entity-heading" data-cy="teamDetailsHeading"><span>Team</span> {{ team.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Team Nm</span>
          </dt>
          <dd>
            <span>{{ team.teamNm }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ team.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ team.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="team.id" :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-details.component.ts"></script>
