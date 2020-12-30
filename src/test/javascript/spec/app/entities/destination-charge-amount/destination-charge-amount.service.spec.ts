import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DestinationChargeAmountService } from 'app/entities/destination-charge-amount/destination-charge-amount.service';
import { IDestinationChargeAmount, DestinationChargeAmount } from 'app/shared/model/destination-charge-amount.model';

describe('Service Tests', () => {
  describe('DestinationChargeAmount Service', () => {
    let injector: TestBed;
    let service: DestinationChargeAmountService;
    let httpMock: HttpTestingController;
    let elemDefault: IDestinationChargeAmount;
    let expectedResult: IDestinationChargeAmount | IDestinationChargeAmount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DestinationChargeAmountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DestinationChargeAmount(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DestinationChargeAmount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DestinationChargeAmount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DestinationChargeAmount', () => {
        const returnedFromService = Object.assign(
          {
            vATEstimated: 'BBBBBB',
            amountEstimated: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DestinationChargeAmount', () => {
        const returnedFromService = Object.assign(
          {
            vATEstimated: 'BBBBBB',
            amountEstimated: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DestinationChargeAmount', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
