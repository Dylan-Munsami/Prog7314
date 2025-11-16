package com.example.greetandeat2.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LocalOrder> __insertionAdapterOfLocalOrder;

  private final EntityDeletionOrUpdateAdapter<LocalOrder> __updateAdapterOfLocalOrder;

  private final SharedSQLiteStatement __preparedStmtOfMarkOrderAsSynced;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocalOrder = new EntityInsertionAdapter<LocalOrder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`orderId`,`restaurantName`,`totalAmount`,`items`,`status`,`createdAt`,`isSynced`,`userId`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LocalOrder entity) {
        if (entity.getOrderId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getOrderId());
        }
        if (entity.getRestaurantName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRestaurantName());
        }
        statement.bindDouble(3, entity.getTotalAmount());
        if (entity.getItems() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getItems());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        statement.bindLong(6, entity.getCreatedAt());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getUserId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getUserId());
        }
      }
    };
    this.__updateAdapterOfLocalOrder = new EntityDeletionOrUpdateAdapter<LocalOrder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `orders` SET `orderId` = ?,`restaurantName` = ?,`totalAmount` = ?,`items` = ?,`status` = ?,`createdAt` = ?,`isSynced` = ?,`userId` = ? WHERE `orderId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LocalOrder entity) {
        if (entity.getOrderId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getOrderId());
        }
        if (entity.getRestaurantName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRestaurantName());
        }
        statement.bindDouble(3, entity.getTotalAmount());
        if (entity.getItems() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getItems());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        statement.bindLong(6, entity.getCreatedAt());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getUserId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getUserId());
        }
        if (entity.getOrderId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getOrderId());
        }
      }
    };
    this.__preparedStmtOfMarkOrderAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET isSynced = 1 WHERE orderId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final LocalOrder order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLocalOrder.insert(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrder(final LocalOrder order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLocalOrder.handle(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markOrderAsSynced(final String orderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkOrderAsSynced.acquire();
        int _argIndex = 1;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkOrderAsSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllOrders(final Continuation<? super List<LocalOrder>> $completion) {
    final String _sql = "SELECT * FROM orders ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<LocalOrder>>() {
      @Override
      @NonNull
      public List<LocalOrder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfRestaurantName = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantName");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final List<LocalOrder> _result = new ArrayList<LocalOrder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LocalOrder _item;
            final String _tmpOrderId;
            if (_cursor.isNull(_cursorIndexOfOrderId)) {
              _tmpOrderId = null;
            } else {
              _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            }
            final String _tmpRestaurantName;
            if (_cursor.isNull(_cursorIndexOfRestaurantName)) {
              _tmpRestaurantName = null;
            } else {
              _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            }
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpItems;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmpItems = null;
            } else {
              _tmpItems = _cursor.getString(_cursorIndexOfItems);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item = new LocalOrder(_tmpOrderId,_tmpRestaurantName,_tmpTotalAmount,_tmpItems,_tmpStatus,_tmpCreatedAt,_tmpIsSynced,_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedOrders(final Continuation<? super List<LocalOrder>> $completion) {
    final String _sql = "SELECT * FROM orders WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<LocalOrder>>() {
      @Override
      @NonNull
      public List<LocalOrder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfRestaurantName = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantName");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final List<LocalOrder> _result = new ArrayList<LocalOrder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LocalOrder _item;
            final String _tmpOrderId;
            if (_cursor.isNull(_cursorIndexOfOrderId)) {
              _tmpOrderId = null;
            } else {
              _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            }
            final String _tmpRestaurantName;
            if (_cursor.isNull(_cursorIndexOfRestaurantName)) {
              _tmpRestaurantName = null;
            } else {
              _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            }
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpItems;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmpItems = null;
            } else {
              _tmpItems = _cursor.getString(_cursorIndexOfItems);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item = new LocalOrder(_tmpOrderId,_tmpRestaurantName,_tmpTotalAmount,_tmpItems,_tmpStatus,_tmpCreatedAt,_tmpIsSynced,_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getOrderById(final String orderId,
      final Continuation<? super LocalOrder> $completion) {
    final String _sql = "SELECT * FROM orders WHERE orderId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (orderId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, orderId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LocalOrder>() {
      @Override
      @Nullable
      public LocalOrder call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfRestaurantName = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantName");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final LocalOrder _result;
          if (_cursor.moveToFirst()) {
            final String _tmpOrderId;
            if (_cursor.isNull(_cursorIndexOfOrderId)) {
              _tmpOrderId = null;
            } else {
              _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            }
            final String _tmpRestaurantName;
            if (_cursor.isNull(_cursorIndexOfRestaurantName)) {
              _tmpRestaurantName = null;
            } else {
              _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            }
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpItems;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmpItems = null;
            } else {
              _tmpItems = _cursor.getString(_cursorIndexOfItems);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _result = new LocalOrder(_tmpOrderId,_tmpRestaurantName,_tmpTotalAmount,_tmpItems,_tmpStatus,_tmpCreatedAt,_tmpIsSynced,_tmpUserId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
