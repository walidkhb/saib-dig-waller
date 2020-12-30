import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransactionHistoryService } from 'app/entities/transaction-history/transaction-history.service';
import { ITransactionHistory, TransactionHistory } from 'app/shared/model/transaction-history.model';

describe('Service Tests', () => {
  describe('TransactionHistory Service', () => {
    let injector: TestBed;
    let service: TransactionHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransactionHistory;
    let expectedResult: ITransactionHistory | ITransactionHistory[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransactionHistoryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TransactionHistory(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransactionHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TransactionHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransactionHistory', () => {
        const returnedFromService = Object.assign(
          {
            dateTime: 'BBBBBB',
            tRReferenceNo: 'BBBBBB',
            beneficiaryName: 'BBBBBB',
            payMode: 'BBBBBB',
            bankName: 'BBBBBB',
            payOutAmount: 'BBBBBB',
            payOutCurrency: 'BBBBBB',
            exchangeRate: 'BBBBBB',
            payInAmount: 'BBBBBB',
            payInCurrency: 'BBBBBB',
            commission: 'BBBBBB',
            status: 'BBBBBB',
            description: 'BBBBBB',
            purposeCode: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransactionHistory', () => {
        const returnedFromService = Object.assign(
          {
            dateTime: 'BBBBBB',
            tRReferenceNo: 'BBBBBB',
            beneficiaryName: 'BBBBBB',
            payMode: 'BBBBBB',
            bankName: 'BBBBBB',
            payOutAmount: 'BBBBBB',
            payOutCurrency: 'BBBBBB',
            exchangeRate: 'BBBBBB',
            payInAmount: 'BBBBBB',
            payInCurrency: 'BBBBBB',
            commission: 'BBBBBB',
            status: 'BBBBBB',
            description: 'BBBBBB',
            purposeCode: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
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

      it('should delete a TransactionHistory', () => {
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
