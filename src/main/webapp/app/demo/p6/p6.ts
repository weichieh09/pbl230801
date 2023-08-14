import axios from 'axios';

const apiBaseUrl = '/api/wcc601';

export default {
  data() {
    return {
      eventZs: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 5,
      },
    };
  },
  created() {
    this.getEventZList();
  },
  methods: {
    editPlayer(team: any): void {
      this.$router.push(`/demo/p4/${team.id}/p5`);
    },
    prepareRemoveTeam(team: any): void {
      this.$refs['removeTeam-modal'].show();
      this.$refs['removeTeam-modal'].team = team;
    },
    removeTeam(): void {
      axios
        .delete(`${apiBaseUrl}/teams/${this.$refs['removeTeam-modal'].team.id}`)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('刪除球隊成功', {
              toaster: 'b-toaster-top-center',
              title: '刪除成功',
              variant: 'success',
              solid: true,
            });
            this.$refs['removeTeam-modal'].hide();
            this.getEventZList();
          } else {
            this.$bvToast.toast('刪除球隊失敗', {
              toaster: 'b-toaster-top-center',
              title: '刪除失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('刪除球隊失敗', {
            toaster: 'b-toaster-top-center',
            title: '刪除失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    createEventZ(): void {
      this.$router.push('/demo/p6/0');
    },
    editTeam(team: any): void {
      this.$router.push(`/demo/p4/${team.id}`);
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getEventZList();
      }
    },
    getEventZList(): void {
      this.eventZs = [];
      axios
        .get(`${apiBaseUrl}/event-zs`, {
          params: {
            sort: 'id,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.eventZs = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        });
    },
  },
};
