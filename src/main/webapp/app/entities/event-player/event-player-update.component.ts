import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IEventPlayer, EventPlayer } from '@/shared/model/event-player.model';
import EventPlayerService from './event-player.service';

const validations: any = {
  eventPlayer: {
    eId: {},
    pId: {},
    chkFg: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class EventPlayerUpdate extends Vue {
  @Inject('eventPlayerService') private eventPlayerService: () => EventPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public eventPlayer: IEventPlayer = new EventPlayer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eventPlayerId) {
        vm.retrieveEventPlayer(to.params.eventPlayerId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.eventPlayer.id) {
      this.eventPlayerService()
        .update(this.eventPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A EventPlayer is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.eventPlayerService()
        .create(this.eventPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A EventPlayer is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.eventPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.eventPlayer[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.eventPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.eventPlayer[field] = null;
    }
  }

  public retrieveEventPlayer(eventPlayerId): void {
    this.eventPlayerService()
      .find(eventPlayerId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.eventPlayer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
