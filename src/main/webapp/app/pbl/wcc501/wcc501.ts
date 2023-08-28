import axios from 'axios';

const apiBaseUrl = '/api/wcc501';

export default {
  data() {
    return {
      teamId: this.$route.params.tId,
      plyrs: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 8,
      },
    };
  },
  created() {
    this.getPlyrList();
  },
  methods: {
    prepareRemovePlyr(plyr: any): void {
      this.$refs['removePlyr-modal'].show();
      this.$refs['removePlyr-modal'].plyr = plyr;
    },
    removePlyr(): void {
      axios
        .delete(`${apiBaseUrl}/players/${this.teamId}/${this.$refs['removePlyr-modal'].plyr.id}`)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('刪除球員成功', {
              toaster: 'b-toaster-top-center',
              title: '刪除成功',
              variant: 'success',
              solid: true,
            });
            this.$refs['removePlyr-modal'].hide();
            this.getPlyrList();
          } else {
            this.$bvToast.toast('刪除球員失敗', {
              toaster: 'b-toaster-top-center',
              title: '刪除失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('刪除球員失敗', {
            toaster: 'b-toaster-top-center',
            title: '刪除失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    createPlyr(): void {
      this.$router.push(`/pbl/wcc401/${this.teamId}/wcc501/0`);
    },
    editPlyr(plyr: any): void {
      this.$router.push(`/pbl/wcc401/${this.teamId}/wcc501/${plyr.id}`);
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getPlyrList();
      }
    },
    getPlyrList(): void {
      axios
        .get(`${apiBaseUrl}/team-players`, {
          params: {
            'tId.equals': this.teamId,
            sort: 'id,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.plyrs = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        });
    },
  },
};
