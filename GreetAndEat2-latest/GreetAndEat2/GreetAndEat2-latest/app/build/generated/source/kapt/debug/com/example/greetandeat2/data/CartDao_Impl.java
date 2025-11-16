package com.example.greetandeat2.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
import java.lang.Integer;
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
public final class CartDao_Impl implements CartDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CartItem> __insertionAdapterOfCartItem;

  private final EntityDeletionOrUpdateAdapter<CartItem> __deletionAdapterOfCartItem;

  private final EntityDeletionOrUpdateAdapter<CartItem> __updateAdapterOfCartItem;

  private final SharedSQLiteStatement __preparedStmtOfClearCart;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSynced;

  public CartDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartItem = new EntityInsertionAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cart_items` (`id`,`name`,`price`,`quantity`,`restaurantId`,`restaurantName`,`imageRes`,`isSynced`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CartItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindDouble(3, entity.getPrice());
        statement.bindLong(4, entity.getQuantity());
        if (entity.getRestaurantId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getRestaurantId());
        }
        if (entity.getRestaurantName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRestaurantName());
        }
        if (entity.getImageRes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getImageRes());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfCartItem = new EntityDeletionOrUpdateAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cart_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CartItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfCartItem = new EntityDeletionOrUpdateAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cart_items` SET `id` = ?,`name` = ?,`price` = ?,`quantity` = ?,`restaurantId` = ?,`restaurantName` = ?,`imageRes` = ?,`isSynced` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CartItem entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindDouble(3, entity.getPrice());
        statement.bindLong(4, entity.getQuantity());
        if (entity.getRestaurantId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getRestaurantId());
        }
        if (entity.getRestaurantName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRestaurantName());
        }
        if (entity.getImageRes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getImageRes());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getId());
        }
      }
    };
    this.__preparedStmtOfClearCart = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cart_items";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE cart_items SET isSynced = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCartItem(final CartItem cartItem,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCartItem.insert(cartItem);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCartItem(final CartItem cartItem,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCartItem.handle(cartItem);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCartItem(final CartItem cartItem,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCartItem.handle(cartItem);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearCart(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearCart.acquire();
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
          __preparedStmtOfClearCart.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsSynced(final String itemId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsSynced.acquire();
        int _argIndex = 1;
        if (itemId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, itemId);
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
          __preparedStmtOfMarkAsSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllCartItems(final Continuation<? super List<CartItem>> $completion) {
    final String _sql = "SELECT * FROM cart_items ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CartItem>>() {
      @Override
      @NonNull
      public List<CartItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
          final int _cursorIndexOfRestaurantName = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantName");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CartItem> _result = new ArrayList<CartItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CartItem _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpRestaurantId;
            if (_cursor.isNull(_cursorIndexOfRestaurantId)) {
              _tmpRestaurantId = null;
            } else {
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
            }
            final String _tmpRestaurantName;
            if (_cursor.isNull(_cursorIndexOfRestaurantName)) {
              _tmpRestaurantName = null;
            } else {
              _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            }
            final Integer _tmpImageRes;
            if (_cursor.isNull(_cursorIndexOfImageRes)) {
              _tmpImageRes = null;
            } else {
              _tmpImageRes = _cursor.getInt(_cursorIndexOfImageRes);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CartItem(_tmpId,_tmpName,_tmpPrice,_tmpQuantity,_tmpRestaurantId,_tmpRestaurantName,_tmpImageRes,_tmpIsSynced,_tmpCreatedAt);
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
  public Object getUnsyncedItems(final Continuation<? super List<CartItem>> $completion) {
    final String _sql = "SELECT * FROM cart_items WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CartItem>>() {
      @Override
      @NonNull
      public List<CartItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantId");
          final int _cursorIndexOfRestaurantName = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurantName");
          final int _cursorIndexOfImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "imageRes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CartItem> _result = new ArrayList<CartItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CartItem _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpRestaurantId;
            if (_cursor.isNull(_cursorIndexOfRestaurantId)) {
              _tmpRestaurantId = null;
            } else {
              _tmpRestaurantId = _cursor.getString(_cursorIndexOfRestaurantId);
            }
            final String _tmpRestaurantName;
            if (_cursor.isNull(_cursorIndexOfRestaurantName)) {
              _tmpRestaurantName = null;
            } else {
              _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            }
            final Integer _tmpImageRes;
            if (_cursor.isNull(_cursorIndexOfImageRes)) {
              _tmpImageRes = null;
            } else {
              _tmpImageRes = _cursor.getInt(_cursorIndexOfImageRes);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CartItem(_tmpId,_tmpName,_tmpPrice,_tmpQuantity,_tmpRestaurantId,_tmpRestaurantName,_tmpImageRes,_tmpIsSynced,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
