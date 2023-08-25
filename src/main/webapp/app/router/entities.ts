import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Team = () => import('@/entities/team/team.vue');
// prettier-ignore
const TeamUpdate = () => import('@/entities/team/team-update.vue');
// prettier-ignore
const TeamDetails = () => import('@/entities/team/team-details.vue');
// prettier-ignore
const Player = () => import('@/entities/player/player.vue');
// prettier-ignore
const PlayerUpdate = () => import('@/entities/player/player-update.vue');
// prettier-ignore
const PlayerDetails = () => import('@/entities/player/player-details.vue');
// prettier-ignore
const TeamPlayer = () => import('@/entities/team-player/team-player.vue');
// prettier-ignore
const TeamPlayerUpdate = () => import('@/entities/team-player/team-player-update.vue');
// prettier-ignore
const TeamPlayerDetails = () => import('@/entities/team-player/team-player-details.vue');
// prettier-ignore
const EventZ = () => import('@/entities/event-z/event-z.vue');
// prettier-ignore
const EventZUpdate = () => import('@/entities/event-z/event-z-update.vue');
// prettier-ignore
const EventZDetails = () => import('@/entities/event-z/event-z-details.vue');
// prettier-ignore
const EventPlayer = () => import('@/entities/event-player/event-player.vue');
// prettier-ignore
const EventPlayerUpdate = () => import('@/entities/event-player/event-player-update.vue');
// prettier-ignore
const EventPlayerDetails = () => import('@/entities/event-player/event-player-details.vue');
// prettier-ignore
const MatchZ = () => import('@/entities/match-z/match-z.vue');
// prettier-ignore
const MatchZUpdate = () => import('@/entities/match-z/match-z-update.vue');
// prettier-ignore
const MatchZDetails = () => import('@/entities/match-z/match-z-details.vue');
// prettier-ignore
const MatchPlayer = () => import('@/entities/match-player/match-player.vue');
// prettier-ignore
const MatchPlayerUpdate = () => import('@/entities/match-player/match-player-update.vue');
// prettier-ignore
const MatchPlayerDetails = () => import('@/entities/match-player/match-player-details.vue');
// prettier-ignore
const VwEventResult = () => import('@/entities/vw-event-result/vw-event-result.vue');
// prettier-ignore
const VwEventResultDetails = () => import('@/entities/vw-event-result/vw-event-result-details.vue');
// prettier-ignore
const TeamEvent = () => import('@/entities/team-event/team-event.vue');
// prettier-ignore
const TeamEventUpdate = () => import('@/entities/team-event/team-event-update.vue');
// prettier-ignore
const TeamEventDetails = () => import('@/entities/team-event/team-event-details.vue');
// prettier-ignore
const VwWcc701Result = () => import('@/entities/vw-wcc-701-result/vw-wcc-701-result.vue');
// prettier-ignore
const VwWcc701ResultDetails = () => import('@/entities/vw-wcc-701-result/vw-wcc-701-result-details.vue');
// prettier-ignore
const UserTeam = () => import('@/entities/user-team/user-team.vue');
// prettier-ignore
const UserTeamUpdate = () => import('@/entities/user-team/user-team-update.vue');
// prettier-ignore
const UserTeamDetails = () => import('@/entities/user-team/user-team-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'team',
      name: 'Team',
      component: Team,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team/new',
      name: 'TeamCreate',
      component: TeamUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team/:teamId/edit',
      name: 'TeamEdit',
      component: TeamUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team/:teamId/view',
      name: 'TeamView',
      component: TeamDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'player',
      name: 'Player',
      component: Player,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'player/new',
      name: 'PlayerCreate',
      component: PlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'player/:playerId/edit',
      name: 'PlayerEdit',
      component: PlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'player/:playerId/view',
      name: 'PlayerView',
      component: PlayerDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-player',
      name: 'TeamPlayer',
      component: TeamPlayer,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-player/new',
      name: 'TeamPlayerCreate',
      component: TeamPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-player/:teamPlayerId/edit',
      name: 'TeamPlayerEdit',
      component: TeamPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-player/:teamPlayerId/view',
      name: 'TeamPlayerView',
      component: TeamPlayerDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-z',
      name: 'EventZ',
      component: EventZ,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-z/new',
      name: 'EventZCreate',
      component: EventZUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-z/:eventZId/edit',
      name: 'EventZEdit',
      component: EventZUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-z/:eventZId/view',
      name: 'EventZView',
      component: EventZDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-player',
      name: 'EventPlayer',
      component: EventPlayer,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-player/new',
      name: 'EventPlayerCreate',
      component: EventPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-player/:eventPlayerId/edit',
      name: 'EventPlayerEdit',
      component: EventPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'event-player/:eventPlayerId/view',
      name: 'EventPlayerView',
      component: EventPlayerDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-z',
      name: 'MatchZ',
      component: MatchZ,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-z/new',
      name: 'MatchZCreate',
      component: MatchZUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-z/:matchZId/edit',
      name: 'MatchZEdit',
      component: MatchZUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-z/:matchZId/view',
      name: 'MatchZView',
      component: MatchZDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-player',
      name: 'MatchPlayer',
      component: MatchPlayer,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-player/new',
      name: 'MatchPlayerCreate',
      component: MatchPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-player/:matchPlayerId/edit',
      name: 'MatchPlayerEdit',
      component: MatchPlayerUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'match-player/:matchPlayerId/view',
      name: 'MatchPlayerView',
      component: MatchPlayerDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: '/vw-event-result',
      name: 'VwEventResult',
      component: VwEventResult,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: '/vw-event-result/:vwEventResultId/view',
      name: 'VwEventResultView',
      component: VwEventResultDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-event',
      name: 'TeamEvent',
      component: TeamEvent,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-event/new',
      name: 'TeamEventCreate',
      component: TeamEventUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-event/:teamEventId/edit',
      name: 'TeamEventEdit',
      component: TeamEventUpdate,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'team-event/:teamEventId/view',
      name: 'TeamEventView',
      component: TeamEventDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: '/vw-wcc-701-result',
      name: 'VwWcc701Result',
      component: VwWcc701Result,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: '/vw-wcc-701-result/:vwWcc701ResultId/view',
      name: 'VwWcc701ResultView',
      component: VwWcc701ResultDetails,
      meta: { authorities: [Authority.ADMIN] },
    },
    {
      path: 'user-team',
      name: 'UserTeam',
      component: UserTeam,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-team/new',
      name: 'UserTeamCreate',
      component: UserTeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-team/:userTeamId/edit',
      name: 'UserTeamEdit',
      component: UserTeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-team/:userTeamId/view',
      name: 'UserTeamView',
      component: UserTeamDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
